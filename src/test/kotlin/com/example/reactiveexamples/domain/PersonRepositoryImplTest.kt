package com.example.reactiveexamples.domain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono

class PersonRepositoryImplTest {

  private val personRepositoryImpl = PersonRepositoryImpl()

@Test
 fun getById() {
   val p1Mono = personRepositoryImpl.getById(1)
   val p1 = p1Mono.block()
  println(p1.toString())
 }
}