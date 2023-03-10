package com.example.listapi.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HeroEntity")
data class HeroEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String,
    var image : String,
    var favorite : Boolean
)