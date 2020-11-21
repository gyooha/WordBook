package io.seroo.data.db.dao

import androidx.room.*
import io.seroo.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(vararg word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun selectWordById(id: Long): Word?

    @Query("SELECT * FROM words")
    suspend fun selectWords(): List<Word>

    @Update
    suspend fun updateWord(vararg word: Word)
}