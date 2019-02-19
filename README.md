# Introduction

This repository serves as a sandbox for testing [Kotlin](https://kotlinlang.org/) libraries, such as 

* [Ktor](https://ktor.io/) - an asynchronous application framework
* [Kodein DI](https://kodein.org/) - a pure Kotlin dependency injection library
* [Kmongo](https://litote.org/kmongo/) - a pure Kotlin library for work with MongoDB (can be used with both sync and async drivers)

# The application

The application provides a RESTful API for fetching random jokes of the ever-so-popular 80-90s action star, _Chuck Norris_. The jokes
are initially loaded from [The Internet Chuck Norris Database](https://www.icndb.com/) and persisted to a local MongoDB

## Pre requisites

* Kotlin
* MongoDB server

# Getting started

The application, once started, can be access via http://localhost:8080. 

1 - Create ADMIN user
```
POST http://localhost:8080/users
{
  "userName": "admin",
  "password": "admin",
  "role": "ADMIN"
}
```
2 - Get auth token
```
POST http://localhost:8080/auth/token
{
  "userName": "admin",
  "password": "admin"
}

--- Response ---
{
  "token": "eyJ0eXAiOiJKV1QiLCJhbGc..."  
}
```
3 - Load database with joke data from external resource.
```
POST http://localhost:8080/app/init -H "Authorization: {token}"
```

# Jokes API

> Fetch all jokes

`GET http://localhost:8080/chucknorris`

> Fetch joke by Id (MongoDB ObjectId)

`GET http://localhost:8080/chucknorris/{id}`

> Fetch joke by external Id

`GET http://localhost:8080/chucknorris/joke/{id}`

> Fetch random joke

`GET http://localhost:8080/chucknorris/random`

> Fetch _n_ number of random jokes

`GET http://localhost:8080/chucknorris/random?quantity={n}`