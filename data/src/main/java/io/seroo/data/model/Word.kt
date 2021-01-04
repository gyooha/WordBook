package io.seroo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Word.TABLE_NAME)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val word: String,
    @ColumnInfo(name = MEAN_FIRST) val meanFirst: String,
    @ColumnInfo(name = MEAN_SECOND) val meanSecond: String,
    @ColumnInfo(name = CREATED_AT) val createdAt: Long,
    @ColumnInfo(name = UPDATED_AT) val updatedAt: Long
) {
    companion object {
        const val TABLE_NAME = "words"
        const val ID = "id"
        const val WORD = "word"
        const val MEAN_FIRST = "mean_first"
        const val MEAN_SECOND = "mean_second"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
    }
}