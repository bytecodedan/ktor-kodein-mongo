package com.martin.config

import com.martin.controller.ApplicationController
import com.martin.controller.AuthenticationController
import com.martin.controller.ChuckNorrisController
import com.martin.controller.UserController
import com.martin.repository.ApplicatonConfigurationRepository
import com.martin.repository.ChuckNorrisRepository
import com.martin.repository.UserRepository
import com.martin.service.ApplicationConfigurationService
import com.martin.service.ApplicationService
import com.martin.service.ChuckNorrisService
import com.martin.service.UserService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.litote.kmongo.coroutine.*
import org.litote.kmongo.reactivestreams.KMongo

val common = Kodein.Module(name = "common") {
    bind("mongoClient") from singleton { KMongo.createClient().coroutine }
    // TODO: Need to understand why this binding does not work at runtime, during retrieval.
//    bind("simpleJwt") from singleton { SimpleJWT(AppConfig.jwtSecret) }
}

val repositories = Kodein.Module(name = "repositories") {
    bind("applicationConfigurationRepository") from singleton { ApplicatonConfigurationRepository(instance("mongoClient")) }
    bind("chuckNorrisRepository") from singleton { ChuckNorrisRepository(instance("mongoClient")) }
    bind("userRepository") from singleton { UserRepository(instance("mongoClient")) }
}

val services = Kodein.Module(name = "services") {
    bind("applicationService") from singleton { ApplicationService(
        instance("applicationConfigurationService"),
        instance("chuckNorrisService"),
        instance("userService")
    ) }
    bind("applicationConfigurationService") from singleton { ApplicationConfigurationService(instance("applicationConfigurationRepository")) }
    bind("chuckNorrisService") from singleton { ChuckNorrisService(instance("chuckNorrisRepository")) }
    bind("userService") from singleton { UserService(instance("userRepository")) }
}

val controllers = Kodein.Module(name = "controllers") {
    bind("authenticationController") from eagerSingleton { AuthenticationController(kodein) }
    bind("applicationController") from eagerSingleton { ApplicationController(kodein) }
    bind("chuckNorrisController") from eagerSingleton { ChuckNorrisController(kodein) }
    bind("userController") from eagerSingleton { UserController(kodein) }
}