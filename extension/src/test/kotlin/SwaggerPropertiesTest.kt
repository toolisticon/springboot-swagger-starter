package io.toolisticon.springboot.swagger

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.CompositePropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import org.springframework.core.io.support.ResourcePropertySource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.io.IOException
import java.util.function.Consumer
import javax.annotation.PostConstruct

@RunWith(SpringRunner::class)
@ContextConfiguration(
  classes = [SwaggerPropertiesTestHelper.MyTestConfiguration::class]
)
abstract class SwaggerPropertiesTestHelper {

  @EnableConfigurationProperties(SwaggerProperties::class)
  class MyTestConfiguration

  companion object {
    val logger: Logger = LoggerFactory.getLogger(SwaggerPropertiesTestHelper::class.java)
  }

  @Autowired
  protected lateinit var properties: SwaggerProperties

  @PostConstruct
  fun log() {
    logger.info("properties=${properties}")
  }

}

class SwaggerPropertiesTest : SwaggerPropertiesTestHelper() {

  @Test
  fun `properties with defaults`() {
    assertThat(properties.enabled).isTrue()
    assertThat(properties.redirect).isFalse()
    assertThat(properties.dockets).isEmpty()
    assertThat(properties.apiInfo).isNotNull
    assertThat(properties.dockets).isEmpty()
  }
}

@ContextConfiguration(
  classes = [WithSingleGroupTest.MyYamlConfiguration::class]
)
class WithSingleGroupTest : SwaggerPropertiesTestHelper() {

  @Configuration
  @EnableConfigurationProperties(SwaggerProperties::class)
  @org.springframework.context.annotation.PropertySource(
    value = ["classpath:/test/WithSingleGroupTest.yml"],
    factory = YamlPropertySourceFactory::class
  )
  class MyYamlConfiguration

  @Test
  fun `has one group in list`() {
    assertThat(properties.dockets).hasSize(1)
  }

  class YamlPropertySourceFactory : PropertySourceFactory {
    @Throws(IOException::class)
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
      val compositePropertySource = CompositePropertySource(name ?: ResourcePropertySource(resource).name)
      YamlPropertySourceLoader().load(name, resource.resource).forEach(Consumer { propertySource: PropertySource<*> -> compositePropertySource.addPropertySource(propertySource) })
      return compositePropertySource
    }
  }

}


@TestPropertySource(locations = ["classpath:test-toplevel-info.properties"])
class RedirectPropertyTest : SwaggerPropertiesTestHelper() {

  @Test
  fun name() {
    assertThat(properties.redirect).isTrue()
  }
}

@TestPropertySource(properties = [
  "swagger.apiInfo.title=Some Title",
  "swagger.apiInfo.description=Some Description",
  "swagger.apiInfo.version=6.6.6",
  "swagger.apiInfo.termsOfServiceUrl=http://terms.url",
  "swagger.apiInfo.license=myLicense",
  "swagger.apiInfo.licenseUrl=http://license.url",
  "swagger.apiInfo.contact.name=Kermit",
  "swagger.apiInfo.contact.url=http://muppets.url",
  "swagger.apiInfo.contact.email=kermit@frog.the",
  "swagger.dockets.foo.basePackage=foo"
])
class OverwriteDocketApiInfoTest : SwaggerPropertiesTestHelper() {

  @Test
  fun `apply global infos`() {
    assertThat(properties.dockets).isNotEmpty
    val foo = properties.dockets["foo"]!!.apiInfo

    assertThat(foo.title).isEqualTo("Some Title")
    assertThat(foo.description).isEqualTo("Some Description")
    assertThat(foo.version).isEqualTo("6.6.6")
    assertThat(foo.termsOfServiceUrl).isEqualTo("http://terms.url")
    assertThat(foo.license).isEqualTo("myLicense")
    assertThat(foo.licenseUrl).isEqualTo("http://license.url")
    assertThat(foo.contact.name).isEqualTo("Kermit")
    assertThat(foo.contact.url).isEqualTo("http://muppets.url")
    assertThat(foo.contact.email).isEqualTo("kermit@frog.the")

  }
}


@TestPropertySource(properties = [
  "swagger.apiInfo.title=Some Title",
  "swagger.apiInfo.description=Some Description",
  "swagger.apiInfo.version=6.6.6",
  "swagger.apiInfo.termsOfServiceUrl=http://terms.url",
  "swagger.apiInfo.license=myLicense",
  "swagger.apiInfo.licenseUrl=http://license.url",
  "swagger.apiInfo.contact.name=Kermit",
  "swagger.apiInfo.contact.url=http://muppets.url",
  "swagger.apiInfo.contact.email=kermit@frog.the",
  // docket: foo
  "swagger.dockets.foo.basePackage=foo",
  // overwrite global
  "swagger.dockets.foo.apiInfo.title=Foo Title",
  "swagger.dockets.foo.apiInfo.description=Foo Description",
  "swagger.dockets.foo.apiInfo.version=6.6.7",
  "swagger.dockets.foo.apiInfo.termsOfServiceUrl=http://foo-terms.url",
  "swagger.dockets.foo.apiInfo.license=fooLicense",
  "swagger.dockets.foo.apiInfo.licenseUrl=http://foo-license.url",
  "swagger.dockets.foo.apiInfo.contact.name=Gonzo",
  "swagger.dockets.foo.apiInfo.contact.url=http://foo-muppets.url",
  "swagger.dockets.foo.apiInfo.contact.email=gonzo@great.the"
])
class DocketApiInfoOverwritesGlobalTest : SwaggerPropertiesTestHelper() {

  @Test
  fun `apply global infos`() {
    assertThat(properties.dockets).isNotEmpty
    val foo = properties.dockets["foo"]!!.apiInfo

    assertThat(foo.title).isEqualTo("Foo Title")
    assertThat(foo.description).isEqualTo("Foo Description")
    assertThat(foo.version).isEqualTo("6.6.7")
    assertThat(foo.termsOfServiceUrl).isEqualTo("http://foo-terms.url")
    assertThat(foo.license).isEqualTo("fooLicense")
    assertThat(foo.licenseUrl).isEqualTo("http://foo-license.url")
    assertThat(foo.contact.name).isEqualTo("Gonzo")
    assertThat(foo.contact.url).isEqualTo("http://foo-muppets.url")
    assertThat(foo.contact.email).isEqualTo("gonzo@great.the")

  }
}
