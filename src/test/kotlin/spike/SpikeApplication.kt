package io.toolisticon.springboot.swagger.spike

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@SpringBootApplication
class SpikeApplication


fun main(vararg args: kotlin.String) {
  SpringApplication.run(SpikeApplication::class.java, *args)
}
