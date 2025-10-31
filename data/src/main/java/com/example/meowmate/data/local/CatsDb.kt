package com.example.meowmate.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatImageEntity::class], version = 1)
abstract class CatsDb : RoomDatabase() {
    abstract fun dao(): CatsDao
}
