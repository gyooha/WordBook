package io.seroo.data.repository

import io.seroo.data.model.Word
import io.seroo.data.model.WordWrapper
import io.seroo.data.source.local.FileLocalDataSource
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

interface FileRepository {
    fun writeFileByWords(words: WordWrapper): Flow<File>
    fun readJsonFileToWords(inputStream: InputStream): Flow<List<Word>>
}

@Singleton
class FileRepositoryImpl @Inject constructor(
    private val fileLocalDataSource: FileLocalDataSource
): FileRepository {
    override fun writeFileByWords(words: WordWrapper): Flow<File> = fileLocalDataSource.writeFileByWords(words)

    override fun readJsonFileToWords(inputStream: InputStream): Flow<List<Word>> =
        fileLocalDataSource.readJsonFileToWords(inputStream)
}