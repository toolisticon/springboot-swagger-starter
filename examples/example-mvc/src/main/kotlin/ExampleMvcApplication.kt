package io.toolisticon.springboot.swagger.example.mvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

@SpringBootApplication
class ExampleMvcApplication {

}

fun main(vararg args: String) = runApplication<ExampleMvcApplication>(*args).let { Unit }
