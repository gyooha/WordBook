package io.seroo.data.service

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.seroo.data.model.Word
import io.seroo.data.model.WordWrapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

interface FileService {
    suspend fun writeWordsToFile(words: WordWrapper): File
    suspend fun readJsonFileToWords(inputStream: InputStream): List<Word>
}

@Singleton
class FileServiceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val json: Json
) : FileService {

    private fun generateFileByName() = File(context.filesDir, FILE_NAME).apply {
        createNewFile()
    }

    override suspend fun writeWordsToFile(words: WordWrapper): File = generateFileByName().apply {
        val jsonStringByWords = json.encodeToString(words)
        writeText(jsonStringByWords, Charsets.UTF_8)
    }

    override suspend fun readJsonFileToWords(inputStream: InputStream): List<Word> {
        BufferedReader(InputStreamReader(inputStream)).use {
            val foldedString = it.readLines().fold("") { acc, s: String? ->
                Log.d("GYH", "folding : $s")
                acc + (s ?: "")
            }.trim()

            Log.d("GYH", "before : $foldedString")
            Log.d("GYH", "test : ${foldedString.startsWith(" ")}")
            /*foldedString.forEach {
//                Log.d("GYH", "\u${it}")
            }*/
            return json.decodeFromString<WordWrapper>(foldedString.dropWhile { it != '{' })
                .also { Log.d("GYH", "result : $it") }
                .words
        }
    }

    companion object {
        private const val FILE_NAME = "WordDatabaseExport.json"
    }
}