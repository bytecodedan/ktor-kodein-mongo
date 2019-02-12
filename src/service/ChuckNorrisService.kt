package com.martin.service

import com.martin.model.ChuchNorrisJokeListApiResponse
import com.martin.model.Joke
import com.martin.repository.ChuckNorrisRepository
import com.mongodb.client.result.UpdateResult
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get


class ChuckNorrisService(private val repository: ChuckNorrisRepository) {

    private fun client(): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }

    suspend fun fetchAllFromApi() : ChuchNorrisJokeListApiResponse {
        return client().get("https://api.icndb.com/jokes")
    }

    suspend fun findAll(): List<Joke> {
        return repository.findAll().toList()
    }

    suspend fun findById(id: String): Joke? {
        return repository.findById(id)
    }

    suspend fun findByExternalId(id: Int): Joke? {
        return repository.findByExternalId(id)
    }

    suspend fun findRandom(quantity: Int): List<Joke> {
        return repository.findRandom(quantity).toList()
    }

    fun save(joke: Joke): UpdateResult? {
        return repository.save(joke)
    }
}