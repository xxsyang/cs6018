package com.example.cs6018_project

import android.graphics.Bitmap

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

    fun getBoardData(): Board {
        if(!this::board.isInitialized) {
            board = Board(Bitmap.createBitmap(2160, 3840, Bitmap.Config.ARGB_8888), 10f, 20, 1, 5, -16777216)
        }
        return board
    }

}