package com.martin.service

import com.martin.model.*
import com.mongodb.client.result.UpdateResult

class ApplicationService(
    private val applicationConfigurationService: ApplicationConfigurationService,
    private val chuckNorrisService: ChuckNorrisService,
    private val userService: UserService
) {
    suspend fun init(): String {
        val appInitialized = getConfig(ConfigurationOption.APP_INIT) ?: "false"

        return when (appInitialized) {
            "false" -> {
                initConfigOptions()
                loadData()
                val config = getConfig(ConfigurationOption.APP_INIT)
                val updatedConfig = Configuration(
                    _id = config?._id,
                    name = config!!.name,
                    value = "true"
                )
                applicationConfigurationService.save(updatedConfig)
                "Application initialized successfully"
            } else -> {
                "Application was already initialized"
            }
        }
    }

    private fun initConfigOptions(): UpdateResult? {
        return applicationConfigurationService.save(Configuration(null, ConfigurationOption.APP_INIT, "false"))
    }

    private suspend fun loadData() {
        chuckNorrisService.fetchAllFromApi().value.map {
            chuckNorrisService.save(
                Joke(_id = it._id, id = it.id, joke = it.joke, categories = it.categories)
            )
        }
        userService.save(User(_id = null, userName = "admin", password = "admin", email = null, role = Role.ADMIN))
    }

    suspend fun getConfig(name: ConfigurationOption): Configuration? {
        return applicationConfigurationService
            .findByName(name)
            .first()
    }
}