package io.toolisticon.springboot.swagger.spike

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class TestApp

fun main(vararg args: kotlin.String) {
    SpringApplication.run(TestApp::class.java, *args)
}