package io.seroo.wordbook.component.game

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.seroo.data.CoroutineDispatcher
import io.seroo.data.repository.WordRepository
import io.seroo.wordbook.component.word.WordUIModel
import io.seroo.wordbook.component.word.toWordByUpdated
import io.seroo.wordbook.component.word.toWordUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.drop
import kotlin.collections.getOrNull
import kotlin.collections.hashMapOf
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.plus
import kotlin.collections.set
import kotlin.collections.shuffled
import kotlin.random.Random

class GameViewModel @ViewModelInject constructor(
    private val wordRepository: WordRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _maxSize = 0

    private var _allWords = listOf<WordUIModel>()

    private var _resultWords = listOf<WordUIModel>()

    private val _currentPosition = MutableLiveData(0)
    val currentPosition: LiveData<Int> get() = _currentPosition

    private val _problemWords = MutableLiveData<List<WordUIModel>>(listOf())
    val problemWords: LiveData<List<WordUIModel>> get() = _problemWords

    private val _isGameInit = MutableLiveData<Boolean>()
    val isGameInit: LiveData<Boolean> get() = _isGameInit

    private val _interval = MutableLiveData<Float>()
    val interval: LiveData<Float> get() = _interval

    private val scoreMap: HashMap<Int, Boolean> = hashMapOf()

    fun getAllWordsAndCompose(currentPosition: Int) {
        wordRepository.selectWordsByLimit(10)
            .zip(wordRepository.selectWordsByLimit(100)) { resultWords, allWords ->
                resultWords.map { it.toWordUIModel() } to allWords.map { it.toWordUIModel() }
            }
            .flowOn(dispatcher.IO)
            .onEach { (resultWords, allWords) ->
                _resultWords = resultWords
                _allWords = allWords
                _maxSize = resultWords.size - 1
                makeWordProblem(currentPosition)
            }
            .onCompletion { _isGameInit.value = true }
            .catch { it.printStackTrace() }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun moveToNextPosition() {
        val currentPosition = _currentPosition.value ?: return
        val nextPosition = currentPosition + 1
        _currentPosition.value = nextPosition
        makeWordProblem(nextPosition)
    }

    private fun makeWordProblem(currentPosition: Int) {
        flowOf(_resultWords)
            .zip(flowOf(_allWords)) { resultWords,  allWords ->
                val currentResult = resultWords[currentPosition]
                getRandomWord(currentResult, allWords)
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

    private tailrec fun getRandomWord(
        resultWord: WordUIModel,
        allWords: List<WordUIModel>,
        problemWords: List<WordUIModel> = listOf()
    ): List<WordUIModel> = when {
        allWords.isEmpty() || problemWords.size == 3 -> problemWords + resultWord
        else -> {
            val randomPosition = Random.nextInt(0, allWords.size)
            val targetWord = allWords[randomPosition]
            val newProblemWords = if (resultWord.id == targetWord.id) {
                problemWords
            } else {
                problemWords + targetWord
            }
            getRandomWord(resultWord, allWords.drop(1), newProblemWords)
        }
    }

    fun getResultByPosition(currentPosition: Int): WordUIModel = _resultWords[currentPosition]

    fun clickWord(currentPosition: Int, clickedWordId: Long, resultId: Long) {
        scoreMap[currentPosition] = resultId == clickedWordId
        _resultWords.getOrNull(currentPosition)?.let { actualWord ->
            wordRepository.updateWords(actualWord.toWordByUpdated())
                .catch { it.printStackTrace() }
                .flowOn(dispatcher.IO)
                .launchIn(viewModelScope)
        }
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
            .catch { it.printStackTrace() }
            .flowOn(dispatcher.Main)
            .launchIn(viewModelScope)
    }

    fun isLastPage() = _currentPosition.value == _maxSize

    fun reset() {
        Log.d("GYH", "gameViewModel.reset")
        _maxSize = 0
        _allWords = listOf()
        _resultWords = listOf()
        _problemWords.value = listOf()
        _currentPosition.value = 0
        _isGameInit.value = false
        scoreMap.clear()
    }
}