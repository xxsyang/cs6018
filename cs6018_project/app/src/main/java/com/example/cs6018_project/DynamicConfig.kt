package com.example.cs6018_project

import com.example.cs6018_project.room.BoardDatabase

object DynamicConfig {

    lateinit var savedBoardDirectory: String
    lateinit var currentEditBoard: String
    lateinit var database: BoardDatabase
    var flagSplashShowed = false

    var serverClientId = "1034891520465-58vdnu8ds3tsnnthf9492unh1kver5du.apps.googleusercontent.com";

    var userHasSignIn = false
    var userId: String? = null
    var userEmail: String? = null

    var flagUsingImport = false
    var imageData: ByteArray = ByteArray(0)
}