package io.toolisticon.springboot.swagger.example.mvc.bar

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
@RequestMapping(value = ["/bar"])
class BarController {

  @ApiOperation("get hello")
  @GetMapping
  fun hello() = ResponseEntity.ok("hello")
}
