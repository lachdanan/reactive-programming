package com.example.reactiveexamples.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PersonRepositoryImpl : PersonRepository {
    val person1 = Person(1, "Antonia", "Scott")
    val person2 = Person(2, "John", "Gutierrez")
    val person3 = Person(3, "Mentor", "Mentor")

    override fun getById(id: Int): Mono<Person> {
        return findAll().filter { it.id == id }.next()
    }

    override fun findAll(): Flux<Person> {
        return Flux.just(person1, person2, person3)
    }
}