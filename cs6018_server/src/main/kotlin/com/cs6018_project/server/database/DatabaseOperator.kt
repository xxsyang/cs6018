package com.cs6018_project.server.database

import com.google.gson.Gson

class DatabaseOperator {
    companion object {

        @Volatile
        private var instance: DatabaseOperator? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DatabaseOperator().also { instance = it }
            }
    }

    fun addPainting(userId: String, email: String, fileName: String, painting: ByteArray) {
        val statement = DatabaseRepository.connection
            .prepareStatement("INSERT INTO t_painting(owner_id, email, file_name, data) VALUES(?, ?, ?, ?);")

        statement.setString(1, userId)
        statement.setString(2, email)
        statement.setString(3, fileName)
        statement.setBytes(4, painting)

        statement.execute()

        statement.close()
    }

    fun listUsers(): ArrayList<String> {
        val statement = DatabaseRepository.connection
            .prepareStatement("SELECT email from t_painting GROUP BY email;")

        val resultSet = statement.executeQuery()
        val result = ArrayList<String>()

        while(resultSet.next()) {
            result.add(resultSet.getString("email"))
        }

        statement.close()

        return result
    }

    fun listUserPaintings(email: String): ArrayList<String> {
        val statement = DatabaseRepository.connection
            .prepareStatement("SELECT * from t_painting WHERE email = ?;")

        statement.setString(1, email)

        val resultSet = statement.executeQuery()
        val result = ArrayList<String>()

        while(resultSet.next()) {
            result.add(resultSet.getString("file_name"))
        }

        statement.close()

        return result
    }

    fun getPaintingByName(fileName: String): ByteArray {
        val statement = DatabaseRepository.connection
            .prepareStatement("SELECT * from t_painting WHERE file_name = ? LIMIT 1;")

        statement.setString(1, fileName)

        val resultSet = statement.executeQuery()

        if (!resultSet.isBeforeFirst) {

            statement.close()

            return ByteArray(0)
        }
        else {
            resultSet.next()
            val result = resultSet.getBytes("data")
            statement.close()
            return result
        }
    }

}