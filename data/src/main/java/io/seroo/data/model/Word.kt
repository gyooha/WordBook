package io.seroo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Word.TABLE_NAME)
data class Word(
    @SerialName(ID)
    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @SerialName(WORD)
    @ColumnInfo(name = WORD)
    val word: String = "",
    @SerialName(MEAN_FIRST)
    @ColumnInfo(name = MEAN_FIRST) val meanFirst: String = "",
    @SerialName(MEAN_SECOND)
    @ColumnInfo(name = MEAN_SECOND) val meanSecond: String = "",
    @SerialName(CREATED_AT)
    @ColumnInfo(name = CREATED_AT) val createdAt: Long = 0L,
    @SerialName(UPDATED_AT)
    @ColumnInfo(name = UPDATED_AT) val updatedAt: Long = 0L
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