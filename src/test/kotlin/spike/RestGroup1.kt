package io.toolisticon.springboot.swagger.spike.group1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/group1")
class Group1Controller {

  @GetMapping(path = arrayOf("group1"))
  fun hello(): String = "hello from group1"

}
