package com.martin.service

import com.martin.model.User
import com.martin.repository.UserRepository
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class UserService(private val repository: UserRepository) {

    suspend fun findAll(): List<User> {
        return repository.findAll().toList()
    }

    suspend fun findById(id: String): User? {
        return repository.findById(id)
    }

    suspend fun findByUserName(userName: String): CoroutineFindPublisher<User> {
        return repository.findByUserName(userName)
    }

    suspend fun findByEmail(email: String): CoroutineFindPublisher<User> {
        return repository.findByEmail(email)
    }

    suspend fun save(user: User): UpdateResult? {
        return repository.save(user)
    }

    suspend fun update(id: String, user: User): UpdateResult? {
        return repository.update(id, user)
    }

    suspend fun delete(id: String): DeleteResult? {
        return repository.delete(id)
    }
}