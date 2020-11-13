package io.seroo.wordbook.component.word

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.seroo.wordbook.TestDataProvider

class WordViewModel @ViewModelInject constructor(): ViewModel() {

    var wordCardList by mutableStateOf(listOf<WordUIModel>())
        private set
    /*private val _wordCardList = MutableLiveData<List<WordUIModel>>()
    val wordCardList: LiveData<List<WordUIModel>> get() = _wordCardList*/

    private var _selectedWordPosition = -1
//    val selectedWordPosition get() = _selectedWordPosition

    init {
        // TODO replace
        wordCardList = TestDataProvider.wordList
    }

    fun setSelectedWordPosition(position: Int) {
        _selectedWordPosition = position
    }

    fun getWord() = wordCardList.getOrNull(_selectedWordPosition)

    fun selectedDone() {
        _selectedWordPosition = -1
    }
}