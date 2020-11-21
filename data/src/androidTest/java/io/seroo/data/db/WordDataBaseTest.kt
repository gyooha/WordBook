package io.seroo.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.seroo.data.db.dao.WordDao
import io.seroo.data.model.Word
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WordDataBaseTest {
    private lateinit var wordDataBase: WordDataBase
    private lateinit var wordDao: WordDao

    @Before
    fun init() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        wordDataBase = Room.inMemoryDatabaseBuilder(context, WordDataBase::class.java).build()
        wordDao = wordDataBase.wordDao()
    }

    @Test
    fun check_word_from_db() = runBlocking {
        wordDao.selectWordById(0)?.let { word ->
            Assert.assertEquals(word.id, 0)
            Assert.assertEquals(word.word, "1")
            Assert.assertEquals(word.meanFirst, "1")
            Assert.assertEquals(word.meanSecond, "1")
            Assert.assertEquals(word.createdAt, 123)
            Assert.assertEquals(word.updatedAt, 234)
            wordDao.deleteWord(word)
        }
        Unit
    }

    @Test
    fun insert_word_from_db() = runBlocking {
        wordDao.insertWord(word)
        wordDao.selectWordById(0)?.let { word ->
            Assert.assertEquals(word.id, 0)
            Assert.assertEquals(word.word, "1")
            Assert.assertEquals(word.meanFirst, "1")
            Assert.assertEquals(word.meanSecond, "1")
            Assert.assertEquals(word.createdAt, 123)
            Assert.assertEquals(word.updatedAt, 234)
            wordDao.deleteWord(word)
        }
        Unit
    }

    @Test
    fun update_word_from_db() = runBlocking {
        wordDao.insertWord(word)
        val updateWord = word.copy(word = "2", meanFirst = "2", meanSecond = "2", createdAt = 234L, updatedAt = 123L)
        wordDao.updateWord(updateWord)
        wordDao.selectWordById(0)?.let { word ->
            Assert.assertEquals(word.id, 0)
            Assert.assertEquals(word.word, "2")
            Assert.assertEquals(word.meanFirst, "2")
            Assert.assertEquals(word.meanSecond, "2")
            Assert.assertEquals(word.createdAt, 234)
            Assert.assertEquals(word.updatedAt, 123)
            wordDao.deleteWord(word)
        }
        Unit
    }

    @Test
    fun get_all_words_from_db() = runBlocking {
        val word2 = word.copy(id = 1, word = "2", meanFirst = "2", meanSecond = "2", createdAt = 234L, updatedAt = 123L)
        wordDao.insertWord(word, word2)
        Assert.assertEquals(wordDao.selectWords().size, 2)
    }
}

private val word = Word(0, "1", "1", "1", 123L, 234L)