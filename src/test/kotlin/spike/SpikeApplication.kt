package io.toolisticon.springboot.swagger.spike

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpikeApplication

fun main(vararg args: kotlin.String) {
  SpringApplication.run(SpikeApplication::class.java, *args)
}
