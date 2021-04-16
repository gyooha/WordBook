package io.seroo.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import io.seroo.data.model.Word

@Dao
interface WordDao {

    @Insert
    suspend fun insertWord(vararg words: Word)

    @Delete
    suspend fun deleteWord(vararg words: Word)

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun selectWordById(id: Long): Word?

    @Query("SELECT * FROM words")
    suspend fun selectWords(): List<Word>

    @Query("SELECT * FROM words LIMIT :limit")
    suspend fun selectWordsByLimit(limit: Int): List<Word>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWord(vararg word: Word)

    @Query("DELETE FROM words")
    suspend fun deleteAll()

    @Transaction
    suspend fun replace(vararg words: Word) {
        deleteAll()
        insertWord(*words)
    }
}