package com.martin.repository

import com.martin.config.AppConfig
import com.martin.model.Model
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineFindPublisher

abstract class AbstractReactiveMongoRepository<T : Model> :
    CrudReactiveMongoRepository<T, String> {

    abstract val collection: CoroutineCollection<T>

    val dbName = AppConfig.mongodb

    override fun findAll(): CoroutineFindPublisher<T> {
        return runBlocking {
            collection.find()
        }
    }

    override fun findById(id: String): T? {
        return runBlocking {
            collection.findOneById(id)
        }
    }

    override fun save(type: T): UpdateResult? {
        return runBlocking {
            collection.save(type)
        }
    }

    override fun update(id: String, type: T): UpdateResult? {
        return runBlocking {
            collection.updateOneById(id, type)
        }
    }

    override fun delete(id: String): DeleteResult? {
        return runBlocking {
            collection.deleteOneById(id)
        }
    }
}