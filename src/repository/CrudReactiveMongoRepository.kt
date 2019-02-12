package com.martin.repository

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface CrudReactiveMongoRepository<T, in ID> {

    fun findById(id: ID): T?

    fun findAll(): CoroutineFindPublisher<T>

    fun save(type: T): UpdateResult?

    fun update(id: ID, type: T): UpdateResult?

    fun delete(id: ID): DeleteResult?
}

