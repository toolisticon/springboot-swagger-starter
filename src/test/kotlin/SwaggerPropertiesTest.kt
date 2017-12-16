package io.toolisticon.springboot.swagger

import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.PostConstruct

@RunWith(SpringRunner::class)
@ContextConfiguration(
  classes = arrayOf(SwaggerPropertiesTestHelper.TestConfig::class)
)
abstract class SwaggerPropertiesTestHelper {

  @EnableConfigurationProperties(SwaggerProperties::class)
  class TestConfig

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
    assertThat(properties.dockets).isEmpty()
  }
}

@Ignore
@TestPropertySource()
class WithSingleGroupTest : SwaggerPropertiesTestHelper() {
  @Test
  fun `has one group in list`() {
    assertThat(properties.dockets).hasSize(1)
  }
}


@TestPropertySource(locations = arrayOf("classpath:test-toplevel-info.properties"))
class GlobalApiInfoPropertyTest : SwaggerPropertiesTestHelper() {

  @Test
  fun name() {
    assertThat(properties.redirect).isTrue()
  }
}
