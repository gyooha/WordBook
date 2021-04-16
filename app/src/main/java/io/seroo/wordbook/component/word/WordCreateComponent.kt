package io.seroo.wordbook.component.word

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.NavigationViewModel

@Composable
fun WordCreateView(navigationViewModel: NavigationViewModel, wordViewModel: WordViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable(onClick = { navigationViewModel.movePreviousOrExit() })
                            .padding(8.dp)
                    )
                }
            )
        },
    ) {
        WordCreateComponent(
            navigationViewModel = navigationViewModel,
            wordViewModel = wordViewModel
        )
    }
}

@Composable
fun WordCreateComponent(navigationViewModel: NavigationViewModel, wordViewModel: WordViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        WordUpdateableComponent(
            wordUIModel = wordViewModel.getWord(),
            onClick = { wordUIModel ->
                wordViewModel.apply {
                    addWord(wordUIModel)
                    selectedDone()
                    navigationViewModel.movePreviousOrExit()
                }
            }
        )
    }
}