package com.martin.controller

import com.martin.model.Role
import com.martin.service.ApplicationService
import com.martin.service.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ApplicationController(override val kodein: Kodein) : KodeinAware {

    private val app: Application by instance()
    private val service: ApplicationService by instance("applicationService")
    private val userService: UserService by instance("userService")

    init {
        app.routing {
            authenticate(optional = true) {
                post("/app/init") {
                    val principal = call.authentication.principal<UserIdPrincipal>()
                    val userName = principal!!.name
                    val user = userService.findByUserName(userName).first()
                    when (user?.role == Role.ADMIN) {
                        true -> {
                            call.respond(HttpStatusCode.OK, service.init())
                        }
                        else -> call.respond(HttpStatusCode.Forbidden, "Restricted endpoint")
                    }
                }
            }
        }
    }
}