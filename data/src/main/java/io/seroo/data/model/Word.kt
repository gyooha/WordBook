package io.seroo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey val id: Long,
    val word: String,
    @ColumnInfo(name = "mean_first") val meanFirst: String,
    @ColumnInfo(name = "mean_second") val meanSecond: String,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)