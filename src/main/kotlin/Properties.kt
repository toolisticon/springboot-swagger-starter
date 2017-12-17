package io.toolisticon.springboot.swagger

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import java.util.function.Supplier

@ConfigurationProperties("swagger")
class SwaggerProperties {

  // enable the feature by default, can be disabled
  var enabled = true
  var redirect = false
  @NestedConfigurationProperty
  var dockets: Map<String, DocketProperty> = mutableMapOf()

  override fun toString(): String {
    return "SwaggerProperties(" +
      "enabled=$enabled, " +
      "redirect=$redirect, " +
      "dockets=$dockets" +
      ")"
  }
}

class DocketProperty {
  @NestedConfigurationProperty
  var apiInfo : ApiInfoProperty = ApiInfoProperty()
  var basePackage: String = ""
  var path: String = ""

  override fun toString(): String {
    return "DocketProperty(" +
      "apiInfo=$apiInfo, " +
      "basePackage='$basePackage', " +
      "path='$path'" +
      ")"
  }
}

class ApiInfoProperty : Supplier<ApiInfo> {

  var title: String = ApiInfo.DEFAULT.title
  var description: String = ApiInfo.DEFAULT.description
  var version: String? = ApiInfo.DEFAULT.version
  var termsOfServiceUrl: String? = ApiInfo.DEFAULT.termsOfServiceUrl
  var license: String = ApiInfo.DEFAULT.license
  var licenseUrl: String = ApiInfo.DEFAULT.licenseUrl

  @NestedConfigurationProperty
  var contact: ContactProperty = ContactProperty()


  override fun toString(): String {
    return "ApiInfoProperty(" +
      "title='$title'," +
      "description='$description', " +
      "version='$version', " +
      "contact='$contact', " +
      "termsOfServiceUrl='$termsOfServiceUrl', " +
      "license='$license', " +
      "licenseUrl='$licenseUrl'" +
      ")"
  }

  override fun get(): ApiInfo = ApiInfo(title, description, version, termsOfServiceUrl, contact.get(), license, licenseUrl, mutableListOf())
}

class ContactProperty : Supplier<Contact> {

  var name: String = ApiInfo.DEFAULT.contact.name
  var url: String = ApiInfo.DEFAULT.contact.url
  var email: String = ApiInfo.DEFAULT.contact.email

  override fun get(): Contact = Contact(name, url, email)

}
