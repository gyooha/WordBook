package io.seroo.wordbook.component.word

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
fun WordCreateView(rootViewModel: RootViewModel, wordViewModel: WordViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    Icon(
                        asset = Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = { rootViewModel.isPopOrExit() }).padding(8.dp)
                    )
                }
            )
        },
    ) {
        WordCreateComponent(rootViewModel = rootViewModel, wordViewModel = wordViewModel)
    }
}

@Composable
fun WordCreateComponent(rootViewModel: RootViewModel, wordViewModel: WordViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var word by savedInstanceState { "" }
        var meanFirst by savedInstanceState { "" }
        var meanSecond by savedInstanceState { "" }

        TextField(
            value = word,
            onValueChange = { word = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
        )
        TextField(
            value = meanFirst,
            onValueChange = { meanFirst = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
        )
        TextField(
            value = meanSecond,
            onValueChange = { meanSecond = it },
            textStyle = TextStyle(Color.White),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
        )
        Button(
            onClick = {
                wordViewModel.apply {
                    addWord(WordUIModel(id = 0, word, meanFirst, meanSecond, System.currentTimeMillis(), System.currentTimeMillis()))
                    selectedDone()
                    rootViewModel.addScreenState(ScreenState.HOME)
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "추가하기")
        }
    }
}