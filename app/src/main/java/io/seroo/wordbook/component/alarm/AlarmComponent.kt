package io.seroo.wordbook.component.alarm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.RootViewModel
import io.seroo.wordbook.component.common.LazyGridForIndexed
import io.seroo.wordbook.component.root.BottomNavigationComponent

@Composable
fun AlarmView(rootViewModel: RootViewModel, alarmViewModel: AlarmViewModel) {
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
        floatingActionButton = {
            FloatingActionButton(onClick = {}, shape = CircleShape) {
                Icon(asset = Icons.Default.Add, modifier = Modifier.size(24.dp))
            }
        },
        bottomBar = {
            BottomNavigationComponent(rootViewModel = rootViewModel)
        }
    ) {
        AlarmListComponent(alarmViewModel = alarmViewModel)
    }
}

@Composable
fun AlarmListComponent(alarmViewModel: AlarmViewModel) {
    LazyGridForIndexed(items = alarmViewModel.alarmList, rows = 2, padding = 8.dp, onClick = {}) { item, index ->
        AlarmCardComponent(item, index)
    }
}

@Composable
fun AlarmCardComponent(item: AlarmUIModel, index: Int) {
    Column(verticalArrangement = Arrangement.Center) {
        Text(text = "알람 ID : ${item.id}")
        Text(text = "알람설정 : ${item.alarmedAt}")
        Text(text = "생성일 : ${item.createdAt}")
    }
}

data class AlarmUIModel(val id: String, val alarmedAt: Long, val createdAt: Long, val updatedAt: Long)