package com.example.reactiveexamples.domain

import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class PersonRepositoryImplTest {

  private val personRepositoryImpl = PersonRepositoryImpl()

    @Test
    fun getById() {
        val p1Mono = personRepositoryImpl.getById(1)
        val p1 = p1Mono.block()
        StepVerifier.create(p1Mono).expectNextCount(1).verifyComplete()
        println(p1.toString())
    }

    @Test
    fun getByIdSubscribe() {
        val p1Mono = personRepositoryImpl.getById(1)

        StepVerifier.create(p1Mono).expectNextCount(1).verifyComplete()

        p1Mono.subscribe { println(it.toString())}
    }

    @Test
    fun getByIdMapSubscribe() {
        val p1Mono = personRepositoryImpl.getById(1)

        StepVerifier.create(p1Mono).expectNextCount(1).verifyComplete()

        p1Mono.map {
            it.firstName
        }.subscribe { println("firstName: $it") }
    }

    @Test
    fun getByIdWithFluxBlock() {
        val p1Flux = personRepositoryImpl.findAll()
        val person = p1Flux.blockFirst()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        println(person.toString())
    }

    @Test
    fun getAllFluxSubscribe() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        p1Flux.subscribe { println(it.toString()) }
    }

    @Test
    fun getAllFluxToListMono() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        val monoList = p1Flux.collectList()
        monoList.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxPlayGround() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        p1Flux.any { it.firstName == "Antonia" }.subscribe { println(it)}
    }

    @Test
    fun getAllFluxWithFilter() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        val p1Mono = p1Flux.filter { it.id == 3 }.next()
        StepVerifier.create(p1Mono).expectNextCount(1).verifyComplete()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFound() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        val p1Mono = p1Flux.filter { it.id == 4 }.next()
        StepVerifier.create(p1Mono).expectNextCount(0).verifyComplete()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFoundAndThrowsException() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        val p1Mono = p1Flux.filter { it.id == 4 }.single()
        StepVerifier.create(p1Mono).expectNextCount(0).verifyError()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFoundAndHandlingException() {
        val p1Flux = personRepositoryImpl.findAll()
        StepVerifier.create(p1Flux).expectNextCount(3).verifyComplete()
        val p1Mono = p1Flux.filter { it.id == 4 }.single()
        StepVerifier.create(p1Mono).expectNextCount(0).verifyError()
        p1Mono.doOnError {
            println("Do on Error")
        }.onErrorReturn(Person(4,"Mr.", "White")).subscribe {
            println(it.toString())
        }
    }

    @Test
    fun findById() {
        val personMono = personRepositoryImpl.getById(2)
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete()
        personMono.subscribe { println(it.toString()) }

    }

    @Test
    fun findByIdWithNoIdFoundAndReturnEmptyMono() {
        val personMono = personRepositoryImpl.getById(4)
        StepVerifier.create(personMono).expectNextCount(0).verifyComplete()
        personMono.subscribe { println(it.toString()) }
    }
}