package com.example.reactiveexamples.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PersonRepositoryImpl : PersonRepository {
    override fun getById(id: Int): Mono<Person> {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flux<Person> {
        TODO("Not yet implemented")
    }
}