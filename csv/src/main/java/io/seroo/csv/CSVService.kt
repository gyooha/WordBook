package io.seroo.csv

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import io.seroo.data.model.Word
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

interface CSVService {
    suspend fun makeCSVFileIntent(words: List<Word>): Intent
    suspend fun readCSVFileToWords(uri: Uri): List<Word>
}

@Singleton
class CSVServiceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : CSVService {

    override suspend fun makeCSVFileIntent(words: List<Word>): Intent {
        return generateCSVFile().apply {
            writeWordInCSVFile(words)
        }.let {
            val contentUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", it)
            val mimeType = context.contentResolver.getType(contentUri) ?: ""

            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(contentUri, mimeType)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            }
        }
    }

    private fun File.writeWordInCSVFile(words: List<Word>) {
        csvWriter().open(this, append = false) {
            words.forEach {
                writeRow(it.id, it.word, it.meanFirst, it.meanSecond, it.createdAt, it.updatedAt)
            }
        }
    }

    private fun generateCSVFile(): File {
        val csvFile = File(context.filesDir, FILE_NAME)
        csvFile.createNewFile()

        return csvFile
    }

    override suspend fun readCSVFileToWords(uri: Uri): List<Word> {
        Log.d("GYH", "readCSVFileToWords")
        return context.contentResolver.openInputStream(uri)?.let {
            val words = mutableListOf<Word>()
            for (line in BufferedReader(InputStreamReader(it)).readLines()) {
                Log.d("GYH", "BufferedReader(InputStreamReader(it)).forEachLine")
                Log.d("GYH", line)
                val wordStrings = line.split(",")

                try {
                    words.add(
                        Word(
                            wordStrings[0].toLong(),
                            wordStrings[1],
                            wordStrings[2],
                            wordStrings[3],
                            wordStrings[4].toLong(),
                            wordStrings[5].toLong()
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    continue
                }
            }

            words
        } ?: listOf()
    }

    companion object {
        private const val FILE_NAME = "WordDatabaseExport.csv"
    }
}