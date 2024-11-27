package com.example.reactiveexamples

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveExamplesApplication

fun main(args: Array<String>) {
    runApplication<ReactiveExamplesApplication>(*args)
}
