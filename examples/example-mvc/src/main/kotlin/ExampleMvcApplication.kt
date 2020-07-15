package io.toolisticon.springboot.swagger.example.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExampleMvcApplication

fun main(vararg args: String) = runApplication<ExampleMvcApplication>(*args).let { Unit }
