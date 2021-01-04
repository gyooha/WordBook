package io.seroo.csv

import android.content.Intent
import android.net.Uri
import io.seroo.data.model.Word
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface CSVRepository {
    fun makeCSVFileIntent(words: List<Word>): Flow<Intent>
    fun readCSVFileToWords(uri: Uri): Flow<List<Word>>
}

@Singleton
class CSVRepositoryImpl @Inject constructor(
    private val csvLocalDataSource: CSVLocalDataSource
) : CSVRepository {
    override fun makeCSVFileIntent(words: List<Word>): Flow<Intent> = csvLocalDataSource.makeCSVFileIntent(words)

    override fun readCSVFileToWords(uri: Uri): Flow<List<Word>> = csvLocalDataSource.readCSVFileToWords(uri)
}