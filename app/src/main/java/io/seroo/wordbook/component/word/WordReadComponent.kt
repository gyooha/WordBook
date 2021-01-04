package io.seroo.wordbook.component.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.NavigationViewModel
import io.seroo.wordbook.ScreenState
import io.seroo.wordbook.component.root.BottomNavigationComponent

@Composable
@ExperimentalFoundationApi
fun WordView(navigationViewModel: NavigationViewModel, wordViewModel: WordViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                actions = {
                    TextButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { navigationViewModel.addScreenState(ScreenState.GAME) }
                    ) {
                        Text(text = "시작", color = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigationViewModel.addScreenState(ScreenState.CREATE) },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, modifier = Modifier.size(24.dp))
            }
        },
        bottomBar = {
            BottomNavigationComponent(navigationViewModel = navigationViewModel)
        },
    ) {
        WordCardListComponent(Modifier.padding(it), wordViewModel = wordViewModel) {
            navigationViewModel.addScreenState(ScreenState.EDITOR)
        }
    }
}

@Composable
@ExperimentalFoundationApi
fun WordCardListComponent(modifier: Modifier, wordViewModel: WordViewModel, onClick: () -> Unit) {
    val wordList by wordViewModel.wordList.observeAsState(listOf())
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        itemsIndexed(wordList) { index, item ->
            WordCardComponent(
                word = item,
                modifier = modifier.clickable {
                    wordViewModel.setSelectedWordPosition(index)
                    onClick.invoke()
                }
            )
        }
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
        if(word.meanFirst.isNotEmpty()) Text(text = word.meanFirst)
        if(word.meanSecond.isNotEmpty()) Text(text = word.meanSecond)
    }
}