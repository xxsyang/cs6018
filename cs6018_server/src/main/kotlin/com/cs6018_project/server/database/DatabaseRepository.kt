package com.cs6018_project.server.database

import org.ktorm.database.Database
import org.sqlite.SQLiteConnection
import java.sql.Connection

object DatabaseRepository {
    val databasePath = "jdbc:sqlite:cs6018_server.db"
    lateinit var connection: Connection

}