package io.toolisticon.springboot.swagger

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.plugin.core.OrderAwarePluginRegistry
import org.springframework.plugin.core.PluginRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.DocumentationPlugin
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc

@ConditionalOnProperty(prefix = "swagger", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration::class)
@EnableConfigurationProperties(SwaggerProperties::class)
class SpringBootSwaggerAutoConfiguration(val properties: SwaggerProperties) {

  companion object {
    val logger: Logger = LoggerFactory.getLogger(SwaggerProperties::class.java)
    const val DUMMY = "DUMMY"
  }

  // Dummy Object to foster injection of List<DocumentationPlugin>, removed during initialization
  @Bean
  fun dummyDocket(): Docket = Docket(DocumentationType.SWAGGER_2).groupName(DUMMY).select().build()

  @Bean
  @Primary
  @Qualifier("documentationPluginRegistry")
  fun swaggerDocumentationPluginRegistry(beanDockets: MutableList<DocumentationPlugin>): PluginRegistry<DocumentationPlugin, DocumentationType> {
    val plugins = beanDockets.filter { it.groupName != DUMMY }.toMutableList()

    properties.dockets.map {
      Docket(DocumentationType.SWAGGER_2)
        .groupName(it.key)
        .apiInfo(it.value.apiInfo.get())
        .select()
        .apis(RequestHandlerSelectors.basePackage(it.value.basePackage))
        .paths(PathSelectors.ant(it.value.path))
        .build()
        .pathMapping(properties.pathMapping)
    }.filter {
      plugins.filter { p -> p.groupName == it.groupName }.isEmpty()
    }.map {
      plugins.add(it)
    }

    logger.info("Register swagger-dockets: {}", plugins.map { it.groupName })

    return OrderAwarePluginRegistry.of(plugins)
  }

  /**
   * Redirects from root to swagger-ui.html, but only if `swagger.enabled=true` is set.
   */
  @ConditionalOnProperty(
    prefix = "swagger",
    name = ["redirect"],
    matchIfMissing = false
  )
  @Bean
  fun redirectSwaggerUI() = object : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
      logger.info("The swagger.redirect property is enabled and web jar support is activated.")
      registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    override fun addViewControllers(registry: ViewControllerRegistry) {
      logger.info("The swagger.redirect property is enabled and http requests are redirected: [/] -> [/swagger-ui.html]")
      registry.addRedirectViewController("/", "/swagger-ui.html")
      super.addViewControllers(registry)
    }
  }
}
