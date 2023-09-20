package com.example.cs6018_project

import android.app.Application
import androidx.room.Room
import com.example.cs6018_project.room.BoardDatabase
import java.io.File

class BoardApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val savedBoardDirectory = File(filesDir.absolutePath + File.separator + "saved")

        if(!savedBoardDirectory.exists()) {
            savedBoardDirectory.mkdirs()
        }

        DynamicConfig.savedBoardDirectory = savedBoardDirectory.absolutePath

        DynamicConfig.database = Room.databaseBuilder(applicationContext, BoardDatabase::class.java, "cs6018_project").allowMainThreadQueries().build()


    }

}