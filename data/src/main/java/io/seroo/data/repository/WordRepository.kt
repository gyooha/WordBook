package io.seroo.data.repository

import io.seroo.data.model.Word
import io.seroo.data.source.WordLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface WordRepository {
    fun insertWords(vararg words: Word): Flow<Unit>
    fun updateWords(vararg words: Word): Flow<Unit>
    fun deleteWords(vararg words: Word): Flow<Unit>
    fun selectWordById(id: Long): Flow<Word>
    fun selectWordsByLimit(limit: Int): Flow<List<Word>>
    fun selectWords(): Flow<List<Word>>
    fun replace(vararg words: Word): Flow<Unit>
}

@Singleton
class WordRepositoryImpl @Inject constructor(
    private val localDataSource: WordLocalDataSource
) : WordRepository {
    override fun insertWords(vararg words: Word): Flow<Unit> = localDataSource.insertWords(*words)

    override fun updateWords(vararg words: Word): Flow<Unit> = localDataSource.updateWords(*words)

    override fun deleteWords(vararg words: Word): Flow<Unit> = localDataSource.deleteWords(*words)

    override fun selectWordById(id: Long): Flow<Word> = localDataSource.selectWordById(id)

    override fun selectWordsByLimit(limit: Int): Flow<List<Word>> = localDataSource.selectWordsByLimit(limit)

    override fun selectWords(): Flow<List<Word>> = localDataSource.selectWords()

    override fun replace(vararg words: Word): Flow<Unit> = localDataSource.replace(*words)
}