package io.seroo.wordbook.component.word

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.seroo.wordbook.TestDataProvider

class WordViewModel @ViewModelInject constructor(): ViewModel() {

    var wordList by mutableStateOf(listOf<WordUIModel>())
        private set
    /*private val _wordCardList = MutableLiveData<List<WordUIModel>>()
    val wordCardList: LiveData<List<WordUIModel>> get() = _wordCardList*/

    val createNewWordUIModel get() = mutableStateOf(WordUIModel("", "", ""))

    private var _selectedWordPosition = -1
//    val selectedWordPosition get() = _selectedWordPosition

    init {
        // TODO replace
//        wordList = TestDataProvider.wordList
    }

    fun setSelectedWordPosition(position: Int) {
        _selectedWordPosition = position
    }

    fun getWord() = wordList.getOrNull(_selectedWordPosition)

    fun addWord(wordUIModel: WordUIModel) {
        wordList = wordList.toMutableList().apply {
            add(wordUIModel)
        }
    }

    fun updateWord(wordUIModel: WordUIModel) {
        wordList = wordList.toMutableList().apply {
            this[_selectedWordPosition] = wordUIModel
        }
    }

    fun selectedDone() {
        _selectedWordPosition = -1
    }
}