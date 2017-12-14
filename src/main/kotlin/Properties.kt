package io.toolisticon.springboot.swagger

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConfigurationProperties("swagger")
class SwaggerProperties() {

  var enabled = false

  var redirect = false

  var name = "default"

  @NestedConfigurationProperty
  var info: ApiInfoProperty = ApiInfoProperty()


  @NestedConfigurationProperty
  var groups: List<SwaggerGroup> = mutableListOf()

  override fun toString(): String {
    return "SwaggerProperties(enabled=$enabled, redirect=$redirect, name='$name', api=$info, groups=$groups)"
  }


}

class SwaggerGroup() {
  lateinit var name: String
}

class ApiInfoProperty() {
  var title: String? = null
  var description: String? = null
  var version: String? = null
  var termsOfServiceUrl: String? = null
  var contact: String? = null
  var license: String? = null
  var licenseUrl: String? = null

  override fun toString(): String {
    return "ApiInfoProperty(title='$title', description='$description', version='$version', termsOfServiceUrl='$termsOfServiceUrl', contact='$contact', license='$license', licenseUrl='$licenseUrl')"
  }


}
