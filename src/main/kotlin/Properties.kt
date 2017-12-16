package io.toolisticon.springboot.swagger

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import springfox.documentation.service.ApiInfo

@ConfigurationProperties("swagger")
class SwaggerProperties() {

  var enabled = false

  var redirect = false

  @NestedConfigurationProperty
  var dockets: Map<String, DocketProperty> = mutableMapOf()

  override fun toString(): String {
    return "SwaggerProperties(enabled=$enabled, redirect=$redirect, dockets=$dockets)"
  }
}

class DocketProperty() {

  @NestedConfigurationProperty
  var apiInfo : ApiInfoProperty = ApiInfoProperty()

  var basePackage: String = ""
  var path: String = ""

}

class ApiInfoProperty() {
  var title: String = ApiInfo.DEFAULT.title
  var description: String = ApiInfo.DEFAULT.description
  var version: String? = ApiInfo.DEFAULT.version
  var termsOfServiceUrl: String? = ApiInfo.DEFAULT.termsOfServiceUrl
  //var contact: ContactProperty = ApiInfo.DEFAULT.contact
  var license: String = ApiInfo.DEFAULT.license
  var licenseUrl: String = ApiInfo.DEFAULT.licenseUrl

  override fun toString(): String {
    return "ApiInfoProperty(title='$title', description='$description', version='$version', termsOfServiceUrl='$termsOfServiceUrl', license='$license', licenseUrl='$licenseUrl')"
  }


}
