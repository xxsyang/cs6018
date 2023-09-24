package com.example.cs6018_project.mvvm

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.cs6018_project.DynamicConfig
import java.io.File
import java.io.FileInputStream


data class Board (
    var bitmap: Bitmap,
    var radius: Float,
    var squareLength: Int,
    var shapeOfPen: Int,
    var size: Int,
    var color: Int
)

object BoardRepository {

    private lateinit var board: Board
    private var currentBoardName = ""

    // fun getBoardList(): List<String> {
    //
    //     return emptyList()
    // }

    private fun reloadBoard(): Board {
        var options = BitmapFactory.Options()
        options.inMutable = true

        // var filePath = DynamicConfig.savedBoardDirectory + File.separator + DynamicConfig.currentEditBoard

        var record = DynamicConfig.database.itemDao().findByName(DynamicConfig.currentEditBoard)

        var bitmap = BitmapFactory.decodeByteArray(record.image, 0, record.image.size, options)

        return Board(bitmap, 10f, 20, 1, 5, -16777216)
    }

    fun getBoardData(): Board {
        if(!this::board.isInitialized) {
            return if(currentBoardName == DynamicConfig.currentEditBoard) {
                board
            } else {
                board = reloadBoard()
                currentBoardName = DynamicConfig.currentEditBoard
                board
            }
        }
        else {
            board = reloadBoard()
            currentBoardName = DynamicConfig.currentEditBoard
            return board
        }

    }

}