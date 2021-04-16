package io.seroo.wordbook.component.word

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                        imageVector = Icons.Default.ArrowBack,
                        null,
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
    WordUpdateableComponent(
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