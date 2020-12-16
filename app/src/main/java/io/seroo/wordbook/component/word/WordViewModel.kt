package io.seroo.wordbook.component.word

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.seroo.data.CoroutineDispatcher
import io.seroo.data.model.Word
import io.seroo.data.repository.WordRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlin.properties.Delegates

class WordViewModel @ViewModelInject constructor(
    private val dispatchers: CoroutineDispatcher,
    private val wordRepository: WordRepository,
): ViewModel() {

    private val _wordList = MutableLiveData<List<WordUIModel>>()
    val wordList: LiveData<List<WordUIModel>> get() = _wordList

    private var _selectedWordPosition by Delegates.observable(-1) { _, old, new -> }

    init {
        wordRepository.selectWords()
            .map { it.map(Word::toWordUIModel) }
            .flowOn(dispatchers.IO)
            .onEach { _wordList.value = it }
            .catch { it.printStackTrace() }
            .flowOn(dispatchers.Main)
            .launchIn(viewModelScope)
    }

    fun setSelectedWordPosition(position: Int) {
        _selectedWordPosition = position
    }

    fun getWord() = wordList.value?.getOrNull(_selectedWordPosition)

    fun addWord(wordUIModel: WordUIModel) {
        wordList.value?.apply {
            val dummyList = toMutableList()
            dummyList.add(wordUIModel)
            _wordList.value = dummyList

            wordRepository.insertWords(wordUIModel.toWord())
                .flowOn(dispatchers.IO)
                .catch { it.printStackTrace() }
                .launchIn(viewModelScope)
        }
    }

    fun updateWord(wordUIModel: WordUIModel) {
        wordList.value?.apply {
            val dummyList = toMutableList()
            dummyList[_selectedWordPosition] = wordUIModel
            _wordList.value = dummyList

            wordRepository.updateWords(wordUIModel.toWord())
                .flowOn(dispatchers.IO)
                .catch { it.printStackTrace() }
                .launchIn(viewModelScope)
        }
    }

    fun selectedDone() {
        _selectedWordPosition = -1
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}