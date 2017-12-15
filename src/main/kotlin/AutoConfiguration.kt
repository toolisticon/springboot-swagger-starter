package io.toolisticon.springboot.swagger

import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.AnnotationAwareOrderComparator
import org.springframework.plugin.core.OrderAwarePluginRegistry
import org.springframework.plugin.core.PluginRegistry
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.DocumentationPlugin
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@ConditionalOnProperty(prefix = "swagger", name = arrayOf("enabled"), havingValue = "true", matchIfMissing = true)
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties::class)
class SpringBootSwaggerAutoConfiguration(val properties : SwaggerProperties) {

  companion object {
      const val DUMMY = "DUMMY"
  }

  // Dummy Object to allow injection of List<DocumentationPlugin>, removed in initialization
  @Bean
  fun dummyDocket() : Docket = Docket(DocumentationType.SWAGGER_2).groupName(DUMMY).select().build()


//  @Bean
//  fun anotherDocket() =  Docket(DocumentationType.SWAGGER_2)
//    .groupName("group2")
//    .apiInfo(ApiInfo.DEFAULT)
//    .select()
//    .apis(RequestHandlerSelectors.basePackage("io.toolisticon.springboot.swagger.spike.group2"))
//    .path(PathSelectors.ant("/group2/**"))
//    .build()

  @Bean
  @Primary
  @Qualifier("documentationPluginRegistry")
  fun swaggerDocumentationPluginRegistry(p: MutableList<DocumentationPlugin>? = mutableListOf()):  PluginRegistry<DocumentationPlugin, DocumentationType> {
    return SwaggerDocumentationPluginRegistry(properties, p!!)
  }


}


class SwaggerDocumentationPluginRegistry(val properties: SwaggerProperties,
                                  val p: MutableList<DocumentationPlugin>) :
  OrderAwarePluginRegistry<DocumentationPlugin, DocumentationType>(p, AnnotationAwareOrderComparator()) {

  companion object : KLogging()

  override fun getPlugins(): List<DocumentationPlugin> {
    val plugins = super.getPlugins().filter { it.groupName != SpringBootSwaggerAutoConfiguration.DUMMY}.toMutableList()

    properties.dockets.map {
      plugins.add(Docket(DocumentationType.SWAGGER_2)
        .groupName(it.key)
        .apiInfo(ApiInfo.DEFAULT)
        .select()
        .apis(RequestHandlerSelectors.basePackage(it.value.basePackage))
        .paths(PathSelectors.ant(it.value.path))
        .build())

    }




    return plugins.toList()
  }


}

