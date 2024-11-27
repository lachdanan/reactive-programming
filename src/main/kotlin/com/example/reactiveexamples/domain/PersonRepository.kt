package com.example.reactiveexamples.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PersonRepository {
    fun getById(id: Int): Mono<Person>
    fun findAll(): Flux<Person>
}