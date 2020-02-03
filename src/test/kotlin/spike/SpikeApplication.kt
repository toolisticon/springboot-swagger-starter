package io.toolisticon.springboot.swagger.spike

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpikeApplication

fun main(vararg args: String) = runApplication<SpikeApplication>(*args).let { Unit }

