package com.example.meowmate.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class CatsDb : RoomDatabase() {
    abstract fun dao(): CatsDao
}
