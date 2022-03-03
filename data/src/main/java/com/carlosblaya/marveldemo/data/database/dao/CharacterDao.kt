package com.carlosblaya.marveldemo.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.carlosblaya.marveldemo.data.database.entities.CharacterDB


@Dao
interface CharacterDao : BaseDao<CharacterDB> {
    @Query("SELECT * FROM character WHERE id = :id")
    fun getCharacter(id: Long):CharacterDB

    @Query("SELECT * FROM character")
    fun getCharacters(): List<CharacterDB>
}