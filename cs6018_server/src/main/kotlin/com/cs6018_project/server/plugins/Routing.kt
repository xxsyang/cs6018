package com.cs6018_project.server.plugins

import com.cs6018_project.server.database.DatabaseOperator
import com.google.gson.Gson
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        put("/upload_image/{file_name}") {
            val fileName = call.parameters["file_name"].orEmpty()
            val email = call.request.headers["email"].orEmpty()
            val userId = call.request.headers["uid"].orEmpty()

            val body = call.receive<ByteArray>()

            DatabaseOperator.getInstance().addPainting(userId, email, fileName, body)

            call.respondText("OK")
        }

        get("/list_users") {
            val result = DatabaseOperator.getInstance().listUsers()

            call.respondText(Gson().toJson(result))
        }

        get("/list_user_painting/{email}") {
            val email = call.parameters["email"].orEmpty()

            val result = DatabaseOperator.getInstance().listUserPaintings(email)

            call.respondText(Gson().toJson(result))
        }

        get("/get_image/{image_name}") {
            val imageName = call.parameters["image_name"].orEmpty()

            if(imageName.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Not found")
            }
            else {
                val savedPainting = DatabaseOperator.getInstance().getPaintingByName(imageName)
                if(savedPainting.isEmpty()) {
                    call.respond(HttpStatusCode.NotFound, "Not found")
                }
                call.respondBytes(
                        savedPainting,
                        ContentType.Image.JPEG
                )
            }
        }

        delete("/remove_image/{image_name}") {
            val imageName = call.parameters["image_name"].orEmpty()
            val email = call.parameters["email"].orEmpty()

            if(imageName.isEmpty()) { }
            else { DatabaseOperator.getInstance().removePaintingByName(imageName, email) }

            call.respondText("OK")
        }

    }
}
