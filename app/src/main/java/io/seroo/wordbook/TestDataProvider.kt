package io.seroo.wordbook

import io.seroo.wordbook.component.alarm.AlarmUIModel
import io.seroo.wordbook.component.word.WordUIModel

object TestDataProvider {
    val wordList: List<WordUIModel> = generateSequence {
        WordUIModel("着", "옷을입다.", "きる")
    }.take(1000)
        .mapIndexed { index, wordUIModel -> if (index % 2 == 0) WordUIModel("着", "도착하다.", "つく") else wordUIModel }
        .toList()

    val alarmList: List<AlarmUIModel> = generateSequence {
        AlarmUIModel("", System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis())
    }.take(5)
        .mapIndexed { index, alarmUIModel -> alarmUIModel.copy(id = "$index") }
        .toList()
}