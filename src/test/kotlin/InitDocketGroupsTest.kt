package io.toolisticon.springboot.swagger

import io.toolisticon.springboot.swagger.spike.SpikeApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.plugin.core.PluginRegistry
import org.springframework.test.context.junit4.SpringRunner
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.DocumentationPlugin

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
