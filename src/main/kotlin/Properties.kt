package io.toolisticon.springboot.swagger

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiInfo.DEFAULT
import springfox.documentation.service.ApiInfo.DEFAULT_CONTACT
import springfox.documentation.service.Contact
import java.util.function.Supplier
import javax.annotation.PostConstruct

@ConfigurationProperties("swagger")
data class SwaggerProperties(
  var enabled: Boolean = true,
  var redirect: Boolean = false,

  @NestedConfigurationProperty
  var apiInfo: ApiInfoProperty = ApiInfoProperty(),

  @NestedConfigurationProperty
  var dockets: Map<String, DocketProperty> = mutableMapOf()
) {

  private fun replaceWithSuper(docketValue: String, globalValue: String, defaultValue: String): String =
    if (docketValue == defaultValue) globalValue else docketValue

  @PostConstruct
  fun init() = dockets.map { it.value.apiInfo }
    .map {
      with(it) {
        title = replaceWithSuper(title, apiInfo.title, DEFAULT.title)
        description = replaceWithSuper(description, apiInfo.description, DEFAULT.description)
        version = replaceWithSuper(version, apiInfo.version, DEFAULT.version)
        termsOfServiceUrl = replaceWithSuper(termsOfServiceUrl, apiInfo.termsOfServiceUrl, DEFAULT.termsOfServiceUrl)
        license = replaceWithSuper(license, apiInfo.license, DEFAULT.license)
        licenseUrl = replaceWithSuper(licenseUrl, apiInfo.licenseUrl, DEFAULT.licenseUrl)
        contact.name = replaceWithSuper(contact.name, apiInfo.contact.name, DEFAULT_CONTACT.name)
        contact.url = replaceWithSuper(contact.url, apiInfo.contact.url, DEFAULT_CONTACT.url)
        contact.email = replaceWithSuper(contact.email, apiInfo.contact.email, DEFAULT_CONTACT.email)
      }
    }
}

data class DocketProperty(
  @NestedConfigurationProperty
  var apiInfo: ApiInfoProperty = ApiInfoProperty(),
  var basePackage: String = "",
  var path: String = ""
)

data class ApiInfoProperty(
  var title: String = DEFAULT.title,
  var description: String = DEFAULT.description,
  var version: String = DEFAULT.version,
  var termsOfServiceUrl: String = DEFAULT.termsOfServiceUrl,
  var license: String = DEFAULT.license,
  var licenseUrl: String = DEFAULT.licenseUrl,

  @NestedConfigurationProperty
  var contact: ContactProperty = ContactProperty()
) : Supplier<ApiInfo> {

  override fun get(): ApiInfo = ApiInfo(title, description, version, termsOfServiceUrl, contact.get(), license, licenseUrl, mutableListOf())
}

data class ContactProperty(
  var name: String = DEFAULT_CONTACT.name,
  var url: String = DEFAULT_CONTACT.url,
  var email: String = DEFAULT_CONTACT.email
) : Supplier<Contact> {

  override fun get(): Contact = Contact(name, url, email)
}
