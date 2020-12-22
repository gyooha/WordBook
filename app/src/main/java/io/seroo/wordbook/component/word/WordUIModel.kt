package io.seroo.wordbook.component.word

import io.seroo.data.model.Word

data class WordUIModel(
    val id: Long,
    val word: String,
    val meanFirst: String,
    val meanSecond: String,
    val createdAt: Long,
    val updatedAt: Long
)

fun Word.toWordUIModel() = WordUIModel(
    id = id,
    word = word,
    meanFirst = meanFirst,
    meanSecond = meanSecond,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun WordUIModel.toWord() = Word(
    id = id,
    word = word,
    meanFirst = meanFirst,
    meanSecond = meanSecond,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun WordUIModel.toWordByUpdated() = Word(
    id = id,
    word = word,
    meanFirst = meanFirst,
    meanSecond = meanSecond,
    createdAt = createdAt,
    updatedAt = System.currentTimeMillis()
)