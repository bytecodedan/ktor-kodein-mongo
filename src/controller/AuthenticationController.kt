package com.martin.controller

//import com.martin.config.SimpleJWT
import com.martin.config.SimpleJWT
import com.martin.service.UserService
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class AuthenticationController(override val kodein: Kodein) : KodeinAware {

    private val app: Application by instance()
    private val userService: UserService by instance("userService")

    init {
        app.routing {

            get("/auth/token") {
                call.respond(HttpStatusCode.MethodNotAllowed)
            }

            post("/auth/token") {
                val post = call.receive<LoginRegister>()
                val user = userService.findByUserName(post.userName).first()
                when (user?.password == post.password) {
                    true -> call.respond(mapOf("token" to SimpleJWT.sign(user!!.userName)))
                    else -> call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
                }
            }
        }
    }
}

//class InvalidCredentialsException(message: String) : RuntimeException(message)
//class NotAuthorizedException(message: String) : RuntimeException(message)

data class LoginRegister(val userName: String, val password: String)