package com.example.cs6018_project.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): List<Item>

    @Query("SELECT * FROM item WHERE name = :name LIMIT 1")
    fun findByName(name: String): Item

    @Insert
    fun insertAll(vararg items: Item)

    @Update
    fun updateImage(item: Item)


}