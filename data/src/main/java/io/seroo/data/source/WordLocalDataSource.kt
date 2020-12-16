package io.seroo.data.source

import io.seroo.data.common.WordNotFoundException
import io.seroo.data.db.dao.WordDao
import io.seroo.data.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface WordLocalDataSource {
    fun insertWords(vararg words: Word): Flow<Unit>
    fun deleteWords(vararg words: Word): Flow<Unit>
    fun updateWords(vararg words: Word): Flow<Unit>
    fun selectWordById(id: Long): Flow<Word>
    fun selectWords(): Flow<List<Word>>
    fun selectWordsForGame(limit: Int): Flow<List<Word>>
}

@Singleton
class WordLocalDataSourceImpl @Inject constructor(private val wordDao: WordDao): WordLocalDataSource {

    override fun insertWords(vararg words: Word): Flow<Unit> = flow {
        emit(wordDao.insertWord(*words))
    }

    override fun updateWords(vararg words: Word): Flow<Unit> = flow {
        emit(wordDao.updateWord(*words))
    }

    override fun deleteWords(vararg words: Word): Flow<Unit> = flow {
        emit(wordDao.deleteWord(*words))
    }

    override fun selectWordById(id: Long): Flow<Word> = flow {
        when (val word = wordDao.selectWordById(id)) {
            null -> throw WordNotFoundException
            else -> emit(word)
        }
    }

    override fun selectWords(): Flow<List<Word>> = flow {
        emit(wordDao.selectWords())
    }

    override fun selectWordsForGame(limit: Int): Flow<List<Word>> = flow {
        emit(wordDao.selectWordsForGame(limit))
    }
}