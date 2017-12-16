package io.toolisticon.springboot.swagger

import io.toolisticon.springboot.swagger.spike.SpikeApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.plugin.core.PluginRegistry
import org.springframework.test.context.junit4.SpringRunner
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.DocumentationPlugin
import springfox.documentation.spring.web.plugins.Docket

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(SpikeApplication::class))
class InitDocketGroupsTest {

  @Autowired
  lateinit var dockets : PluginRegistry<DocumentationPlugin, DocumentationType>

  @Test
  fun `find both group 1+2 dockets`() {
    val plugins = dockets.plugins
    assertThat(plugins).hasSize(2)
    assertThat(plugins.map { it.groupName }).containsOnly("group1", "group2")
  }
}

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(SpikeApplication::class, TestUniqueGroupTest.TestConfig::class))
class TestUniqueGroupTest {

  @Autowired
  lateinit var dockets : PluginRegistry<DocumentationPlugin, DocumentationType>

  @Test
  fun `find groups 1+2+3 dockets`() {
    val plugins = dockets.plugins
    assertThat(plugins).hasSize(3)
    assertThat(plugins.map { it.groupName }).containsOnly("group1", "group2", "group3")
  }

  @Configuration
  class TestConfig {

    @Bean
    fun group2() : Docket = Docket(DocumentationType.SWAGGER_2).groupName("group2").select().build()

    @Bean
    fun group3() : Docket = Docket(DocumentationType.SWAGGER_2).groupName("group3").select().build()

  }
}
