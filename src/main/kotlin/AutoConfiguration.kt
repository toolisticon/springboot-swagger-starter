package io.toolisticon.springboot.swagger

import io.toolisticon.springboot.swagger.SwaggerProperties.Companion.PREFIX
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@ConditionalOnProperty(prefix = PREFIX, name = arrayOf("enabled"), havingValue = "true", matchIfMissing = true )
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties::class)
class SpringBootSwaggerAutoConfiguration(val properties: SwaggerProperties) {

    @Bean
    fun docket() : Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .groupName("default")
                .apiInfo(ApiInfo.DEFAULT)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.holisticon.ranked.view"))
                .paths(PathSelectors.ant("/view/**"))
                .build()
    }

}
