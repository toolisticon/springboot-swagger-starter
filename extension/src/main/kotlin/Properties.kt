package io.toolisticon.springboot.swagger

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiInfo.DEFAULT
import springfox.documentation.service.ApiInfo.DEFAULT_CONTACT
import springfox.documentation.service.Contact
import java.util.function.Supplier
import javax.annotation.PostConstruct

@ConfigurationProperties("swagger")
@ConstructorBinding
data class SwaggerProperties(
  /**
   * Enable or disable docket registration. Defaults to true.
   */
  val enabled: Boolean = true,
  /**
   * Enable redirect of "/" request to Swagger-UI. Defaults to false.
   */
  val redirect: Boolean = false,
  /**
   * Servlet path mapping. Defaults to "/".
   */
  var pathMapping: String = "/",
  /**
   * API Info property.
   */
  @NestedConfigurationProperty
  val apiInfo: ApiInfoProperty = ApiInfoProperty(),
  /**
   * Docket definition.
   */
  @NestedConfigurationProperty
  var dockets: Map<String, DocketProperty> = mutableMapOf()
) {

  @PostConstruct
  fun processDefaults() {
    this.dockets = processDefaults(dockets, this.apiInfo)
  }

  companion object {
    private fun processDefaults(dockets: Map<String, DocketProperty>, apiInfo: ApiInfoProperty) = dockets.mapValues {
      it.value.copy(apiInfo = ApiInfoProperty(
        title = replaceWithSuper(it.value.apiInfo.title, apiInfo.title, DEFAULT.title),
        description = replaceWithSuper(it.value.apiInfo.description, apiInfo.description, DEFAULT.description),
        version = replaceWithSuper(it.value.apiInfo.version, apiInfo.version, DEFAULT.version),
        termsOfServiceUrl = replaceWithSuper(it.value.apiInfo.termsOfServiceUrl, apiInfo.termsOfServiceUrl, DEFAULT.termsOfServiceUrl),
        license = replaceWithSuper(it.value.apiInfo.license, apiInfo.license, DEFAULT.license),
        licenseUrl = replaceWithSuper(it.value.apiInfo.licenseUrl, apiInfo.licenseUrl, DEFAULT.licenseUrl),
        contact = it.value.apiInfo.contact.copy(
          name = replaceWithSuper(it.value.apiInfo.contact.name, apiInfo.contact.name, DEFAULT_CONTACT.name),
          url = replaceWithSuper(it.value.apiInfo.contact.url, apiInfo.contact.url, DEFAULT_CONTACT.url),
          email = replaceWithSuper(it.value.apiInfo.contact.email, apiInfo.contact.email, DEFAULT_CONTACT.email)
        )
      ))
    }

    private fun replaceWithSuper(docketValue: String, globalValue: String, defaultValue: String): String =
      if (docketValue == defaultValue) globalValue else docketValue
  }
}

data class DocketProperty(
  /**
   * Docket API info property, will overide the globally defined.
   */
  @NestedConfigurationProperty
  var apiInfo: ApiInfoProperty = ApiInfoProperty(),
  /**
   * Package of the controllers in this docket.
   */
  var basePackage: String = "",
  /**
   * URL path of this docket.
   */
  var path: String = ""
)

@ConstructorBinding
data class ApiInfoProperty(
  val title: String = DEFAULT.title,
  val description: String = DEFAULT.description,
  val version: String = DEFAULT.version,
  val termsOfServiceUrl: String = DEFAULT.termsOfServiceUrl,
  val license: String = DEFAULT.license,
  val licenseUrl: String = DEFAULT.licenseUrl,

  @NestedConfigurationProperty
  val contact: ContactProperty = ContactProperty()
) : Supplier<ApiInfo> {

  override fun get(): ApiInfo = ApiInfo(title, description, version, termsOfServiceUrl, contact.get(), license, licenseUrl, mutableListOf())
}

@ConstructorBinding
data class ContactProperty(
  val name: String = DEFAULT_CONTACT.name,
  val url: String = DEFAULT_CONTACT.url,
  val email: String = DEFAULT_CONTACT.email
) : Supplier<Contact> {

  override fun get(): Contact = Contact(name, url, email)
}
