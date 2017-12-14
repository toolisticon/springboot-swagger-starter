package io.toolisticon.springboot.swagger

import mu.KLogging
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar
import org.springframework.core.type.AnnotationMetadata
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@ConditionalOnProperty(prefix = "swagger", name = arrayOf("enabled"), havingValue = "true", matchIfMissing = true)
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties::class)
class SpringBootSwaggerAutoConfiguration : ImportBeanDefinitionRegistrar {

  companion object : KLogging()


  override fun registerBeanDefinitions(importingClassMetadata: AnnotationMetadata, registry: BeanDefinitionRegistry) {
    val r : ConfigurableListableBeanFactory= if (registry is ConfigurableListableBeanFactory) registry else throw IllegalStateException("error")
    val docket = Docket(DocumentationType.SWAGGER_2)

      .groupName("group1")
      .apiInfo(ApiInfo.DEFAULT)
      .select()
      .apis(RequestHandlerSelectors.basePackage("io.toolisticon.springboot.swagger.spike.group1"))
      .paths(PathSelectors.ant("/group1/**"))
      .build()

   logger.info { "docket: ${docket}" }

    registry.registerSingleton("docket-group1", docket)
  }

  @Bean
  fun docket(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .groupName("default")
      .apiInfo(ApiInfo.DEFAULT)
      .select()
      .apis(RequestHandlerSelectors.basePackage("de.holisticon.ranked.view"))
      .paths(PathSelectors.ant("/view/**"))
      .build()
  }

}
