package com.carlosblaya.marveldemo.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
    @PrimaryKey
    var id: String,
    var name: String? = ""
)



