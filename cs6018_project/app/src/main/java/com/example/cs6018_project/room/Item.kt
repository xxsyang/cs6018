package com.example.cs6018_project.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    var name: String?,


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) var image: ByteArray
)
