package com.martin.repository

import com.martin.model.Joke
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.coroutine.*

class ChuckNorrisRepository(private val client: CoroutineClient) : AbstractReactiveMongoRepository<Joke>() {

    override val collection = client.getDatabase(dbName).getCollection<Joke>("chucknorrisjokes")

    fun findByExternalId(id: Int): Joke? {
        return runBlocking {
            collection.findOne("{ id: $id }")
        }
    }

    fun findRandom(quantity: Int): CoroutineAggregatePublisher<Joke> {
        return runBlocking {
            collection.aggregate<Joke>("{ ${MongoOperator.sample}: { size: $quantity }}")
        }
    }
}