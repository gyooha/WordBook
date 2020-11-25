package io.seroo.wordbook.component.word

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import io.seroo.wordbook.NavigationViewModel

@Composable
fun WordEditView(navigationViewModel: NavigationViewModel, wordViewModel: WordViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    Icon(
                        asset = Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = { navigationViewModel.movePreviousOrExit() })
                            .padding(8.dp)
                    )
                }
            )
        },
    ) {
        WordEditComponent(navigationViewModel = navigationViewModel, wordViewModel = wordViewModel)
    }
}

@Composable
fun WordEditComponent(navigationViewModel: NavigationViewModel, wordViewModel: WordViewModel) {
    WordUpdateComponent(
        wordUIModel = wordViewModel.getWord(),
        onClick = { wordUIModel ->
            wordViewModel.apply {
                updateWord(wordUIModel)
                selectedDone()
                navigationViewModel.movePreviousOrExit()
            }
        }
    )
}