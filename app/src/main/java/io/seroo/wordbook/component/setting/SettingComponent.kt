package io.seroo.wordbook.component.setting

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.seroo.wordbook.NavigationViewModel
import io.seroo.wordbook.component.root.BottomNavigationComponent

@ExperimentalFoundationApi
@Composable
fun SettingView(
    navigationViewModel: NavigationViewModel,
    settingViewModel: SettingViewModel,
    activityResultLauncher: ActivityResultLauncher<String>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "WordBook") },
                navigationIcon = {
                    Icon(
                        Icons.Default.ArrowBack,
                        modifier = Modifier.clickable(onClick = { navigationViewModel.movePreviousOrExit() }).padding(8.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, shape = CircleShape) {
                Icon(Icons.Default.Add, modifier = Modifier.size(24.dp))
            }
        },
        bottomBar = {
            BottomNavigationComponent(navigationViewModel = navigationViewModel)
        }
    ) {
        SettingListComponent(settingViewModel = settingViewModel, activityResultLauncher)
    }
}

@ExperimentalFoundationApi
@Composable
fun SettingListComponent(settingViewModel: SettingViewModel, activityResultLauncher: ActivityResultLauncher<String>) {
    val settings by settingViewModel.settings.observeAsState(listOf())
    val context = AmbientContext.current

    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(settings) {
            when (it) {
                SettingType.SAVE -> {
                    SettingItemComponent(
                        it,
                        modifier = Modifier.clickable {
                            settingViewModel.saveWordsToJsonFile()
                        }.height(40.dp)
                    )
                }
                SettingType.LOAD -> {
                    SettingItemComponent(
                        it,
                        modifier = Modifier.clickable {
                            activityResultLauncher.launch("text/*")
                        }.height(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SettingItemComponent(settingType: SettingType, modifier: Modifier = Modifier, composable: @Composable () -> Unit = {}) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        Text(text = settingType.title, fontSize = 16.sp)
        composable()
    }
}

data class AlarmUIModel(val id: String, val alarmedAt: Long, val createdAt: Long, val updatedAt: Long)