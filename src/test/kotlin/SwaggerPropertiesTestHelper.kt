package io.toolisticon.springboot.swagger

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@EnableConfigurationProperties(SwaggerProperties::class)
open abstract class SwaggerPropertiesTestHelper {

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

class WithSingleGroupTest : SwaggerPropertiesTestHelper() {
    @Test
    fun `has one group in list`() {
        assertThat(properties.groups).hasSize(1)
    }
}