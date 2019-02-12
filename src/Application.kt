package com.martin

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.martin.config.*
import io.ktor.auth.jwt.jwt
import io.ktor.jackson.*
import io.ktor.locations.Locations
import io.ktor.server.netty.Netty
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

fun main() {
    embeddedServer(Netty, port = AppConfig.port.toInt()) {
        kodeinApplication {
            install(CallLogging) {
                level = Level.INFO
                filter { call -> call.request.path().startsWith("/") }
            }

            install(CORS) {
                method(HttpMethod.Options)
                method(HttpMethod.Put)
                method(HttpMethod.Delete)
                method(HttpMethod.Patch)
                header(HttpHeaders.Authorization)
                header("MyCustomHeader")
                allowCredentials = true
                anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
            }

            install(ShutDownUrl.ApplicationCallFeature) {
                shutDownUrl = AppConfig.shutDownUrl
                // A function that will be executed to get the exit code of the process
                exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
            }

            val simpleJwt = SimpleJWT

            install(Authentication) {
                jwt {
                    verifier(simpleJwt.verifier)
                    validate {
                        UserIdPrincipal(it.payload.getClaim("name").asString())
                    }
                }
            }

            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }

            install(Locations)

            import(common)
            import(repositories)
            import(services)
            import(controllers)

        }
    }.start(wait = true)
}

@kotlin.jvm.JvmOverloads
fun Application.kodeinApplication(kodeinMapper : Kodein.MainBuilder.(Application) -> Unit = {}) {
    val app = this
    val kodein = Kodein {
        bind<Application>() with singleton { app }
        kodeinMapper(this, app)
    }
}