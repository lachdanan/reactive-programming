package com.example.reactiveexamples.domain

import org.junit.jupiter.api.Test

class PersonRepositoryImplTest {

  private val personRepositoryImpl = PersonRepositoryImpl()

    @Test
    fun getById() {
        val p1Mono = personRepositoryImpl.getById(1)
        val p1 = p1Mono.block()
        println(p1.toString())
    }

    @Test
    fun getByIdSubscribe() {
        val p1Mono = personRepositoryImpl.getById(1)
        p1Mono.subscribe { println(it.toString())}
    }

    @Test
    fun getByIdMapSubscribe() {
        val p1Mono = personRepositoryImpl.getById(1)
        p1Mono.map {
            it.firstName
        }.subscribe { println("firstName: $it") }
    }

    @Test
    fun getByIdWithFluxBlock() {
        val p1Flux = personRepositoryImpl.findAll()
        val person = p1Flux.blockFirst()
        println(person.toString())
    }

    @Test
    fun getAllFluxSubscribe() {
        val p1Flux = personRepositoryImpl.findAll()
        p1Flux.subscribe { println(it.toString()) }
    }

    @Test
    fun getAllFluxToListMono() {
        val p1Flux = personRepositoryImpl.findAll()
        val monoList = p1Flux.collectList()
        monoList.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxPlayGround() {
        val p1Flux = personRepositoryImpl.findAll()
        p1Flux.any { it.firstName == "Antonia" }.subscribe { println(it)}
    }

    @Test
    fun getAllFluxWithFilter() {
        val p1Flux = personRepositoryImpl.findAll()
        val p1Mono = p1Flux.filter { it.id == 3 }.next()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFound() {
        val p1Flux = personRepositoryImpl.findAll()
        val p1Mono = p1Flux.filter { it.id == 4 }.next()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFoundAndThrowsException() {
        val p1Flux = personRepositoryImpl.findAll()
        val p1Mono = p1Flux.filter { it.id == 4 }.single()
        p1Mono.subscribe {
            println(it.toString())
        }
    }

    @Test
    fun getAllFluxWithFilterAndNotFoundAndHandlingException() {
        val p1Flux = personRepositoryImpl.findAll()
        val p1Mono = p1Flux.filter { it.id == 4 }.single()
        p1Mono.doOnError {
            println("Do on Error")
        }.onErrorReturn(Person(4,"Mr.", "White")).subscribe {
            println(it.toString())
        }
    }

    @Test
    fun findById() {
        val personMono = personRepositoryImpl.getById(2)
        personMono.subscribe { println(it.toString()) }

    }

    @Test
    fun findByIdWithNoIdFoundAndReturnEmptyMono() {
        val personMono = personRepositoryImpl.getById(4)
        personMono.subscribe { println(it.toString()) }
    }
}