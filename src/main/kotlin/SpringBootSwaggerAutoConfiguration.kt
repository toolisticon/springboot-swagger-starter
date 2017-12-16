package io.toolisticon.springboot.swagger

import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.plugin.core.OrderAwarePluginRegistry
import org.springframework.plugin.core.PluginRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
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
class SpringBootSwaggerAutoConfiguration(val properties: SwaggerProperties) {

  companion object : KLogging() {
    const val DUMMY = "DUMMY"
  }

  // Dummy Object to allow injection of List<DocumentationPlugin>, removed in initialization
  @Bean
  fun dummyDocket(): Docket = Docket(DocumentationType.SWAGGER_2).groupName(DUMMY).select().build()

  @Bean
  @Primary
  @Qualifier("documentationPluginRegistry")
  fun swaggerDocumentationPluginRegistry(beanDockets: MutableList<DocumentationPlugin>): PluginRegistry<DocumentationPlugin, DocumentationType> {
    val propertyDockets = properties.dockets.map {
      Docket(DocumentationType.SWAGGER_2)
        .groupName(it.key)
        .apiInfo(ApiInfo.DEFAULT)
        .select()
        .apis(RequestHandlerSelectors.basePackage(it.value.basePackage))
        .paths(PathSelectors.ant(it.value.path))
        .build()
    }

    val plugins = beanDockets.filter { it.groupName != SpringBootSwaggerAutoConfiguration.DUMMY }.toMutableList()
    plugins.addAll(propertyDockets)

    logger.info { "Register swagger-dockets: ${plugins.map { it.groupName }}" }

    return OrderAwarePluginRegistry.create(plugins)
  }

  /**
   * Redirects from root to swagger-ui.html, but only if `swagger.enabled=true` is set.
   */
  @ConditionalOnProperty(prefix = "swagger", name = arrayOf("redirect"), matchIfMissing = false)
  @Bean
  fun redirectSwaggerUI() = object : WebMvcConfigurerAdapter() {
    override fun addViewControllers(registry: ViewControllerRegistry) {
      registry.addRedirectViewController("/", "/swagger-ui.html")
      super.addViewControllers(registry)
    }
  }
}
