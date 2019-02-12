package com.martin.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.util.KtorExperimentalAPI

object AppConfig {

    @KtorExperimentalAPI
    private val config = HoconApplicationConfig(ConfigFactory.load())

    val appName : String = config.property("ktor.projectName").getString()

    val env : String = System.getProperty("ENV") ?: "dev"

    val mongodb : String = config.propertyOrNull("ktor.database.mongo.$env")?.getString() ?: "$appName-dev"

    val jwtSecret : String = config.propertyOrNull("ktor.auth.jwt.secret")?.getString() ?: "$appName-jwt-secret"

    val port : String = config.propertyOrNull("ktor.deployment.port")?.getString() ?: "80"

    val shutDownUrl : String = config.property("ktor.deployment.shutdown.url").getString()
}

// Workaround for binding issue
object SimpleJWT {
    private val secret = AppConfig.jwtSecret
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier = JWT.require(algorithm).build()
    fun sign(name: String): String = JWT.create().withClaim("name", name).sign(algorithm)
}