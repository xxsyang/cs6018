package com.example.cs6018_project.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class BoardDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}