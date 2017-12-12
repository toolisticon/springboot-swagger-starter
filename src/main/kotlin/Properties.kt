package io.toolisticon.springboot.swagger

import io.toolisticon.springboot.swagger.SwaggerProperties.Companion.PREFIX
import lombok.Data
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty


@ConfigurationProperties(PREFIX)
@Data
class SwaggerProperties {

    companion object {
        const val PREFIX= "swagger"
    }

    var enabled = false

    var redirect = false

    var name = "default"



    @NestedConfigurationProperty
    var groups: List<SwaggerGroup> = mutableListOf()

}

@Data
class SwaggerGroup {
    lateinit var name : String
}