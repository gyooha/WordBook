package io.seroo.wordbook.component.game

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.seroo.data.CoroutineDispatcher
import io.seroo.data.repository.WordRepository
import io.seroo.wordbook.component.word.WordUIModel
import io.seroo.wordbook.component.word.toWordUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class GameViewModel @ViewModelInject constructor(
    private val wordRepository: WordRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    /*private val _maxSize = MutableLiveData<Int>(0)
    val maxSize: LiveData<Int> get() = _maxSize*/

    private var _maxSize = 0

    private var _allWords = listOf<WordUIModel>()

    /*private val _allWords = MutableLiveData<List<WordUIModel>>()
    val allWords: LiveData<List<WordUIModel>> get() = _allWords*/

    private var _resultWords = listOf<WordUIModel>()

    /*private val _resultWords = MutableLiveData<List<WordUIModel>>()
    val resultWords: LiveData<List<WordUIModel>> get() = _resultWords*/

    private val _currentPosition = MutableLiveData(0)
    val currentPosition: LiveData<Int> get() = _currentPosition

    private val _isCurrentGameEnd = MutableLiveData<Boolean>(false)
    val isCurrentGameEnd: LiveData<Boolean> get() = _isCurrentGameEnd

    private val _problemWords = MutableLiveData<List<WordUIModel>>(listOf())
    val problemWords: LiveData<List<WordUIModel>> get() = _problemWords

    val isLastPage: LiveData<Boolean> = Transformations.map(_currentPosition) {
        it == _maxSize
    }

    private val _interval = MutableLiveData<Float>()
    val interval: LiveData<Float> get() = _interval

    private val scoreMap: HashMap<Int, Boolean> = hashMapOf()

    fun init() {
        wordRepository.selectWordsForGame(10)
            .map { it.map { it.toWordUIModel() } }
            .flowOn(dispatcher.IO)
            .onEach {
                _resultWords = it
                _maxSize = it.size - 1
            }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)

        wordRepository.selectWordsForGame(100)
            .map { it.map { it.toWordUIModel() } }
            .flowOn(dispatcher.IO)
            .onEach { _allWords = it }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun moveToNextPosition() {
        flowOf(2L)
            .map {
                delay(it)
                it
            }
            .flowOn(dispatcher.Default)
            .onEach {
                _currentPosition.value = _currentPosition.value!! + 1
                makeWordProblem(_currentPosition.value!!)
            }
            .flowOn(dispatcher.Main)
            .launchIn(viewModelScope)
    }

    fun makeWordProblem(currentPosition: Int) {
        flowOf(_resultWords)
            .zip(flowOf(_allWords)) { resultWords,  allWords ->
                val currentResult = resultWords[currentPosition]
                val list = listOf(currentResult) + allWords
                    .filter { currentResult.id != it.id }
                    .take(3)
                list
            }
            .map { it.shuffled(Random) }
            .flowOn(dispatcher.Default)
            .onEach {
                _problemWords.value = it
            }
            .catch { it.printStackTrace() }
            .flowOn(dispatcher.Main)
            .launchIn(viewModelScope)
    }

    fun getResultByPosition(currentPosition: Int): WordUIModel = _resultWords[currentPosition]

    fun clickWord(currentPosition: Int, clickedWordId: Long, resultId: Long) {
        scoreMap[currentPosition] = resultId == clickedWordId
    }

    fun flowInterval(target: Float) {
        flow {
            var attempt = 0F
            while (target > attempt) {
                delay(1000)
                emit(++attempt)
            }
        }.flowOn(dispatcher.Default)
            .onStart { _interval.value = target }
            .onEach { _interval.value = target - it }
            .catch { Log.e("GYH", it.message ?: "") }
            .flowOn(dispatcher.Main)
            .launchIn(viewModelScope)
    }
}