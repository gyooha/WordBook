package io.seroo.wordbook.component.root

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.RootViewModel
import io.seroo.wordbook.ScreenState
import io.seroo.wordbook.component.alarm.AlarmListComponent
import io.seroo.wordbook.component.alarm.AlarmViewModel
import io.seroo.wordbook.component.word.WordCardListComponent
import io.seroo.wordbook.component.word.WordEditorComponent
import io.seroo.wordbook.component.word.WordViewModel
import io.seroo.wordbook.ui.purple200

@Composable
fun RootComponent(rootViewModel: RootViewModel, wordViewModel: WordViewModel, alarmViewModel: AlarmViewModel) {
    val screenState = rootViewModel.currentScreenState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    when (screenState) {
                        ScreenState.EDITOR, ScreenState.ALARM -> {
                            Icon(
                                asset = Icons.Default.ArrowBack,
                                modifier = Modifier.clickable(onClick = { rootViewModel.isPopOrExit() })
                            )
                        }
                        ScreenState.HOME -> Unit
                    }
                },
                actions = {
                    when (screenState) {
                        ScreenState.HOME -> {
                            TextButton(
                                modifier = Modifier.padding(8.dp),
                                onClick = { }
                            ) {
                                Text(text = "시작", color = Color.White)
                            }
                        }
                        ScreenState.EDITOR -> {

                        }
                        ScreenState.ALARM -> Unit
                    }
                }
            )
        },
        floatingActionButton = {
            when (screenState) {
                ScreenState.ALARM -> {
                    FloatingActionButton(onClick = {}, shape = CircleShape) {
                        Icon(asset = Icons.Default.Add, modifier = Modifier.size(16.dp))
                    }
                }
                ScreenState.EDITOR -> {
                    FloatingActionButton(onClick = {}, shape = CircleShape) {
                        Icon(asset = Icons.Default.Add, modifier = Modifier.size(16.dp))
                    }
                }
                ScreenState.HOME -> Unit
            }
        },
        bottomBar = {
            BottomNavigationComponent(rootViewModel = rootViewModel)
        }
    ) {
        when (screenState) {
            ScreenState.HOME -> {
                WordCardListComponent(wordViewModel = wordViewModel) {
                    rootViewModel.addScreenState(ScreenState.EDITOR)
                }
            }
            ScreenState.EDITOR -> WordEditorComponent(wordViewModel)
            ScreenState.ALARM -> AlarmListComponent(alarmViewModel = alarmViewModel)
        }
    }
}

@Composable
private fun BottomNavigationComponent(rootViewModel: RootViewModel) {
    when (val screenState = rootViewModel.currentScreenState) {
        ScreenState.HOME, ScreenState.ALARM -> {
            BottomNavigation(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.onPrimary)) {
                Row {
                    TextButton(modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { rootViewModel.resetScreenState() }) {
                        Text(text = "홈", color = if (screenState == ScreenState.HOME) purple200 else Color.White)
                    }

                    TextButton(modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { rootViewModel.addScreenState(ScreenState.ALARM) }) {
                        Text(text = "알림", color = if (screenState == ScreenState.ALARM) purple200 else Color.White)
                    }
                }
            }
        }
        ScreenState.EDITOR -> Unit
    }
}