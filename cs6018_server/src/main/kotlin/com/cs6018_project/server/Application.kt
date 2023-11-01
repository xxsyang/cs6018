package com.cs6018_project.server

import com.cs6018_project.server.database.DatabaseRepository

import com.cs6018_project.server.plugins.*
import io.ktor.network.sockets.*

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.support.sqlite.SQLiteDialect
import org.sqlite.SQLiteConnection
import java.sql.DriverManager

fun main() {
    setupApplication()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}

fun createTables() {
    val createTableUser = """
        CREATE TABLE IF NOT EXISTS "t_user" (
        "id"	INTEGER,
        "user_id"	TEXT,
        "email"	TEXT,
        PRIMARY KEY("id" AUTOINCREMENT)
    );
    """
    val createTablePainting = """
        CREATE TABLE IF NOT EXISTS "t_painting" (
            "id"	INTEGER,
            "owner_id"	INTEGER,
            "email"	TEXT,
            "file_name" TEXT,
            "data"	BLOB,
            PRIMARY KEY("id" AUTOINCREMENT)
        );
    """

    var statement = DatabaseRepository.connection.createStatement()
    statement.execute(createTableUser)
    statement.execute(createTablePainting)
    statement.close()

}

fun setupApplication() {
    Class.forName("org.sqlite.JDBC")
    DatabaseRepository.connection = DriverManager.getConnection(DatabaseRepository.databasePath)

    createTables()
}
