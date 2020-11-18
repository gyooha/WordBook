package io.seroo.wordbook.component.word

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.RootViewModel
import io.seroo.wordbook.ScreenState

@Composable
fun WordEditView(rootViewModel: RootViewModel, wordViewModel: WordViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    Icon(
                        asset = Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = { rootViewModel.isPopOrExit() })
                            .padding(8.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, shape = CircleShape) {
                Icon(asset = Icons.Default.Add, modifier = Modifier.size(16.dp))
            }
        },
    ) {
        WordEditComponent(rootViewModel = rootViewModel, wordViewModel = wordViewModel)
    }
}

@Composable
fun WordEditComponent(rootViewModel: RootViewModel, wordViewModel: WordViewModel) {
    wordViewModel.getWord()?.let { actualWord ->
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var word by savedInstanceState { actualWord.word }
            var meanFirst by savedInstanceState { actualWord.meanFirst }
            var meanSecond by savedInstanceState { actualWord.meanSecond }

            TextField(
                value = word,
                onValueChange = { word = it },
                textStyle = TextStyle(Color.White),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            TextField(
                value = meanFirst,
                onValueChange = { meanFirst = it },
                textStyle = TextStyle(Color.White),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            TextField(
                value = meanSecond,
                onValueChange = { meanSecond = it },
                textStyle = TextStyle(Color.White),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )
            Button(
                onClick = {
                    wordViewModel.apply {
                        updateWord(WordUIModel(word, meanFirst, meanSecond))
                        selectedDone()
                        rootViewModel.addScreenState(ScreenState.HOME)
                    }
                },
                enabled = actualWord.word.isNotEmpty() || actualWord.meanFirst.isNotEmpty() || actualWord.meanSecond.isNotEmpty()
            ) {
                Text(text = "수정하기")
            }
        }
    }
}