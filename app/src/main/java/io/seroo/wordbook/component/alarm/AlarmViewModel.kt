package io.seroo.wordbook.component.alarm

import androidx.compose.runtime.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import io.seroo.wordbook.TestDataProvider

class AlarmViewModel @ViewModelInject constructor(): ViewModel() {
    var alarmList by mutableStateOf(listOf<AlarmUIModel>())
        private set

    init {
        alarmList = TestDataProvider.alarmList
    }
}