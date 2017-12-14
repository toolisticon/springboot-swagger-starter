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
class SpikeApplication {

  fun dockets(): List<Docket> {
    return listOf(Docket(DocumentationType.SWAGGER_2)
      .groupName("group1")
      .apiInfo(ApiInfo.DEFAULT)
      .select()
      .apis(RequestHandlerSelectors.basePackage("io.toolisticon.springboot.swagger.spike.group1"))
      .paths(PathSelectors.ant("/group1/**"))
      .build(),
      Docket(DocumentationType.SWAGGER_2)
        .groupName("group2")
        .apiInfo(ApiInfo.DEFAULT)
        .select()
        .apis(RequestHandlerSelectors.basePackage("io.toolisticon.springboot.swagger.spike.group2"))
        .paths(PathSelectors.ant("/group2/**"))
        .build())
  }

}


fun main(vararg args: kotlin.String) {
  SpringApplication.run(SpikeApplication::class.java, *args)
}
