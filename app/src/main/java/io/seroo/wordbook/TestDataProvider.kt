package io.seroo.wordbook

import io.seroo.wordbook.component.alarm.AlarmUIModel
import io.seroo.wordbook.component.word.WordUIModel

object TestDataProvider {
    val alarmList: List<AlarmUIModel> = generateSequence {
        AlarmUIModel("", System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis())
    }.take(5)
        .mapIndexed { index, alarmUIModel -> alarmUIModel.copy(id = "$index") }
        .toList()
}