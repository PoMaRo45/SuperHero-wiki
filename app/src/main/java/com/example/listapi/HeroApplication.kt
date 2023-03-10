package com.example.listapi

import android.app.Application
import androidx.room.Room
import com.example.listapi.database.HeroDatabase

class HeroApplication: Application() {
    companion object{
        lateinit var database: HeroDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
        HeroDatabase::class.java,
        "HeroDatabase").build()
    }
}