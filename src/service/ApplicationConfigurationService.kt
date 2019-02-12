package com.martin.service

import com.martin.model.Configuration
import com.martin.model.ConfigurationOption
import com.martin.repository.ApplicatonConfigurationRepository
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import io.ktor.client.request.get
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class ApplicationConfigurationService(private val repository: ApplicatonConfigurationRepository) {

    suspend fun findAll(): List<Configuration> {
        return repository.findAll().toList()
    }

    suspend fun findById(id: String): Configuration? {
        return repository.findById(id)
    }

    suspend fun findByName(name: ConfigurationOption): CoroutineFindPublisher<Configuration> {
        return repository.findByName(name)
    }

    fun save(configuration: Configuration): UpdateResult? {
        return repository.save(configuration)
    }

    fun update(id: String, configuration: Configuration): UpdateResult? {
        return repository.update(id = id, type = configuration)
    }

    fun delete(id: String): DeleteResult? {
        return repository.delete(id)
    }
}