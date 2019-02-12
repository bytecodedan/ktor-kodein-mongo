package com.martin.controller

import com.martin.model.User
import com.martin.service.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class UserController(override val kodein: Kodein) : KodeinAware {

    private val app: Application by instance()
    private val service: UserService by instance("userService")

    init {
        app.routing {

            get("/users/{id?}") {
                val id = call.parameters["id"]
                when(id) {
                    null -> call.respond(service.findAll())
                    else -> service.findById(id)?.let { call.respond(it) }
                }
            }

            post("/users") {
                val user = call.receive<User>()
                service.save(user)?.let { call.respond(it) }
            }

            patch("/users/{id}") {
                val id = call.parameters["id"]
                val user = call.receive<User>()
                service.update(id!!, user)?.let { call.respond(it) }
            }

            delete("/users/{id}") {
                val id = call.parameters["id"]
                service.delete(id!!)?.let { call.respond(it) }
            }
        }
    }
}