package io.seroo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import io.seroo.data.db.dao.WordDao
import io.seroo.data.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}