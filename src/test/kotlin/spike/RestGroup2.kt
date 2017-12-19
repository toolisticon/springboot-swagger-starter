package io.toolisticon.springboot.swagger.spike.group2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/group2")
class Group2Controller {

  @GetMapping(path = ["group2"])
  fun hello(): String = "hello from group2"

}


