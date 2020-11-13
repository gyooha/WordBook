package io.seroo.wordbook.component.word

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.component.common.LazyGridForIndexed

@Composable
fun WordCardListComponent(wordViewModel: WordViewModel, onClick: () -> Unit = { Unit }) {
    LazyGridForIndexed(
        items = wordViewModel.wordCardList,
        rows = 2,
        padding = 8.dp,
        onClick = {
            wordViewModel.setSelectedWordPosition(it)
            onClick.invoke()
        }
    ) { item, _ ->
        WordCardComponent(word = item)
    }
}

@Composable
private fun WordCardComponent(word: WordUIModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = word.word)
        if(word.meanSecond.isNotEmpty()) Text(text = word.meanSecond)
        if(word.meanFirst.isNotEmpty()) Text(text = word.meanFirst)
    }
}

@Composable
fun WordEditorComponent(wordViewModel: WordViewModel) {
    wordViewModel.getWord()?.let { actualWord ->
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(value = actualWord.word, onValueChange = { Unit })
            TextField(value = actualWord.meanFirst, onValueChange = { Unit })
            TextField(value = actualWord.meanSecond, onValueChange = { Unit })
        }
    }
}

data class WordUIModel(val word: String, val meanFirst: String, val meanSecond: String)