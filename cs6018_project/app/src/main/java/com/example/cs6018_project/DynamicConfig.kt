package com.example.cs6018_project

import com.example.cs6018_project.room.BoardDatabase

object DynamicConfig {

    lateinit var savedBoardDirectory: String
    lateinit var currentEditBoard: String
    lateinit var database: BoardDatabase
    var flagSplashShowed = false

}