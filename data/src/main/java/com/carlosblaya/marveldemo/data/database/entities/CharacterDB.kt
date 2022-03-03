package com.carlosblaya.marveldemo.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterDB(
    @PrimaryKey
    var id: Long,
    var name: String = "",
    var description: String = "",
    var thumbnail: String = ""
)



