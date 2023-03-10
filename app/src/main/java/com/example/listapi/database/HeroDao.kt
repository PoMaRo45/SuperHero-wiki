package com.example.listapi.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HeroDao {
    @Query("SELECT * FROM HeroEntity")
    fun getAllHeroes(): MutableList<HeroEntity>
    @Insert
    fun addFavorite(hero : HeroEntity)
    @Delete
    fun deleteFavorite(hero : HeroEntity)
}