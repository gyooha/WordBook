package io.seroo.csv

import android.content.Intent
import android.net.Uri
import io.seroo.data.model.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

interface CSVLocalDataSource {
    fun makeCSVFileIntent(words: List<Word>): Flow<Intent>
    fun readCSVFileToWords(uri: Uri): Flow<List<Word>>
}

@Singleton
class CSVLocalDataSourceImpl @Inject constructor(
    private val csvService: CSVService
) : CSVLocalDataSource {
    override fun makeCSVFileIntent(words: List<Word>): Flow<Intent> = flow {
        emit(csvService.makeCSVFileIntent(words))
    }

    override fun readCSVFileToWords(uri: Uri): Flow<List<Word>> = flow {
        emit(csvService.readCSVFileToWords(uri))
    }
}