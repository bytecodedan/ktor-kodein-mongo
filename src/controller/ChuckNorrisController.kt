package com.martin.controller

import com.martin.service.ChuckNorrisService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ChuckNorrisController(override val kodein: Kodein) : KodeinAware {

    private val app: Application by instance()
    private val service: ChuckNorrisService by instance("chuckNorrisService")

    init {
        app.routing {

            get("/chucknorris/{id?}") {
                val id = call.parameters["id"]
                when (id) {
                    null -> call.respond(service.findAll())
                    else -> service.findById(id)?.let { call.respond(it) }
                }
            }

            get("/chucknorris/random/{quantity?}") {
                val quantity = call.parameters["quantity"]?.toInt() ?: 1
                call.respond(service.findRandom(quantity))
            }

            get("/chucknorris/joke/{id}") {
                val id = call.parameters["id"]!!.toInt()
                service.findByExternalId(id)?.let { call.respond(it) }
            }
        }
    }
}