package io.toolisticon.springboot.swagger

import io.toolisticon.springboot.swagger.SwaggerPropertiesTestHelper.TestConfig
import io.toolisticon.springboot.swagger.spike.SpikeApplication
import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.PostConstruct
import java.io.IOException
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.ApplicationContextInitializer



@RunWith(SpringRunner::class)
@ContextConfiguration(
  classes = arrayOf(SwaggerPropertiesTestHelper.TestConfig::class)
)
abstract class SwaggerPropertiesTestHelper {

  @EnableConfigurationProperties(SwaggerProperties::class)
  class TestConfig {

  }

  companion object : KLogging()

  @Autowired
  protected lateinit var properties: SwaggerProperties

  @PostConstruct
  fun log() {
    logger.info { "properties=${properties}" }
  }

}

@Ignore
class SwaggerPropertiesTest : SwaggerPropertiesTestHelper() {

  @Test
  fun `properties with defaults`() {
    assertThat(properties.enabled).isFalse()
    assertThat(properties.redirect).isFalse()
    assertThat(properties.groups).isEmpty()
  }
}

@Ignore
@TestPropertySource()
class WithSingleGroupTest : SwaggerPropertiesTestHelper() {
  @Test
  fun `has one group in list`() {
    assertThat(properties.groups).hasSize(1)
  }
}


@TestPropertySource(locations = arrayOf("classpath:test-toplevel-info.properties"))
class GlobalApiInfoPropertyTest : SwaggerPropertiesTestHelper() {

  @Test
  fun name() {
    assertThat(properties.redirect).isTrue()
    assertThat(properties.info.title).isEqualTo("top level title")
    assertThat(properties.info.description).isEqualTo("top level description")
    assertThat(properties.info.version).isEqualTo("1.0.1")
    assertThat(properties.info.termsOfServiceUrl).isEqualTo("http://termsofservice.dummy")
    assertThat(properties.info.contact).isEqualTo("email@diedetektive.org")
    assertThat(properties.info.license).isEqualTo("Free")
    assertThat(properties.info.licenseUrl).isEqualTo("http://some-license.ig")
  }
}
