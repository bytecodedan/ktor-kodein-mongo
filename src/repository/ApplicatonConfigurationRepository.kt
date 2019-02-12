package com.martin.repository

import com.martin.model.Configuration
import com.martin.model.ConfigurationOption
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineFindPublisher


class ApplicatonConfigurationRepository(private val client: CoroutineClient) : AbstractReactiveMongoRepository<Configuration>() {

    override val collection = client.getDatabase(dbName).getCollection<Configuration>("appConfigurations")

    fun findByName(name: ConfigurationOption): CoroutineFindPublisher<Configuration> {
        return runBlocking {
            collection.find("{ name: '$name' }")
        }
    }
}
