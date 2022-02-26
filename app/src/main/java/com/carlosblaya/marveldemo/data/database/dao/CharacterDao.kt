package com.carlosblaya.marveldemo.data.database.dao

import androidx.room.Dao
import com.carlosblaya.marveldemo.data.database.entities.Character

@Dao
interface CharacterDao : BaseDao<Character> {

}