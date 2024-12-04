package com.example.reactiveexamples.domain

import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono

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
}