package io.toolisticon.springboot.swagger.example.mvc.foo

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
@RequestMapping(value = ["/foo"])
class FooController {

  @ApiOperation("get hello")
  @GetMapping
  fun hello() = ResponseEntity.ok("hello")
}
