package io.seroo.data.source

import io.seroo.data.db.dao.WordDao
import io.seroo.data.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WordLocalDataSource @Inject constructor(private val wordDao: WordDao): LocalDataSource {

    fun insertWord(vararg word: Word) = Unit

    fun deleteWord(word: Word) = Unit

    fun selectWordById(id: Long): Flow<Word> = flow {
        when (val word = wordDao.selectWordById(id)) {
            null -> throw NoSuchElementException("word is null")
            else -> emit(word)
        }
    }

    fun selectWord(): List<Word> = listOf()
}