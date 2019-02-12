package com.martin.model

import io.ktor.auth.Principal
import java.time.LocalDateTime

data class ChuchNorrisJokeListApiResponse(val type: String, val value: List<Joke>)

data class ChuchNorrisJokeApiResponse(val type: String, val value: Joke)

data class Joke(
    override val _id: String?,
    val id: Int,
    val joke: String,
    val categories: List<String>?
//    override val created: LocalDateTime,
//    override val updated: LocalDateTime?
): Model

data class User(
    override val _id: String?,
    val userName: String,
    val email: String?,
    val password: String,
    val role: Role?
//    override val created: LocalDateTime,
//    override val updated: LocalDateTime?
): Model, Principal

enum class Role { ADMIN, USER }

interface Model {
    val _id: String?
//    val created: LocalDateTime
//    val updated: LocalDateTime?
}

data class Configuration(
    override val _id: String?,
    val name: ConfigurationOption,
    val value: String
//    override val created: LocalDateTime,
//    override val updated: LocalDateTime?
): Model

enum class ConfigurationOption { APP_INIT }