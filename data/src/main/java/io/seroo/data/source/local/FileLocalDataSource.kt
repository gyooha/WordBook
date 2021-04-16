package io.seroo.data.source.local

import io.seroo.data.model.Word
import io.seroo.data.model.WordWrapper
import io.seroo.data.service.FileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

interface FileLocalDataSource {
    fun writeFileByWords(words: WordWrapper): Flow<File>
    fun readJsonFileToWords(inputStream: InputStream): Flow<List<Word>>
}

@Singleton
class FileLocalDataSourceImpl @Inject constructor(
    private val fileService: FileService
) : FileLocalDataSource {
    override fun writeFileByWords(words: WordWrapper): Flow<File> = flow {
        emit(fileService.writeWordsToFile(words))
    }

    override fun readJsonFileToWords(inputStream: InputStream): Flow<List<Word>> = flow {
        emit(fileService.readJsonFileToWords(inputStream))
    }
}

