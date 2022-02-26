package com.carlosblaya.marveldemo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosblaya.marveldemo.data.database.dao.CharacterDao
import com.carlosblaya.marveldemo.data.database.entities.Character

@Database(
    entities = [
        Character::class
    ], version = 1, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract val characterDao: CharacterDao
}

