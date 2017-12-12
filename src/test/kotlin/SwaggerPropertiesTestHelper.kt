package io.toolisticon.springboot.swagger

import mu.KLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@EnableConfigurationProperties(SwaggerProperties::class)
open abstract class SwaggerPropertiesTestHelper {

    companion object : KLogging()

    @Autowired
    protected lateinit var properties: SwaggerProperties

}

class SwaggerPropertiesTest : SwaggerPropertiesTestHelper() {

    @Test
    fun `properties with defaults`() {
        assertThat(properties.enabled).isFalse()
        assertThat(properties.redirect).isFalse()
        assertThat(properties.groups).isEmpty()
    }
}

@TestPropertySource()
class WithSingleGroupTest : SwaggerPropertiesTestHelper() {
    @Test
    fun `has one group in list`() {
        assertThat(properties.groups).hasSize(1)
    }
}

@TestPropertySource("classpath:test/GlobalApiInfoPropertyTest.yml")
class GlobalApiInfoPropertyTest : SwaggerPropertiesTestHelper() {


    @Test
    fun name() {
logger.info { "${properties.api}" }
    }
}