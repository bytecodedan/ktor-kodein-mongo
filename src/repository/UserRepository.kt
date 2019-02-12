package com.martin.repository

import com.martin.model.User
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class UserRepository(private val client: CoroutineClient) : AbstractReactiveMongoRepository<User>() {

    override val collection = client.getDatabase(dbName).getCollection<User>("users")

    fun findByUserName(userName: String): CoroutineFindPublisher<User> {
        return runBlocking {
            collection.find("{ userName: '$userName' }")
        }
    }

    fun findByEmail(email: String): CoroutineFindPublisher<User> {
        return runBlocking {
            collection.find("{ email: '$email' }")
        }
    }
}