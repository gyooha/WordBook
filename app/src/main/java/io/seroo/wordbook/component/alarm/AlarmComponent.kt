package io.seroo.wordbook.component.alarm

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.seroo.wordbook.component.common.LazyGridForIndexed

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