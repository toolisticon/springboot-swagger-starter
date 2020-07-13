package io.toolisticon.springboot.swagger.spike.test

import io.toolisticon.springboot.swagger.spike.group1.DummyRequest
import io.toolisticon.springboot.swagger.spike.group1.DummyResponse
import io.toolisticon.springboot.swagger.spike.group1.repeatString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Group1ControllerTest {

  @Test
  fun `repeat hello 2 times`() {
    assertThat(repeatString(DummyRequest("hello", 2))).isEqualTo(DummyResponse("hellohello"))
  }
}
