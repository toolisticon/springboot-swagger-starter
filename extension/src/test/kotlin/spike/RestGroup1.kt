package io.toolisticon.springboot.swagger.spike.group1

import io.swagger.annotations.*
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("/group1")
class Group1Controller {

  @GetMapping(path = ["/"])
  fun hello(): String = "hello from group1"

  @ApiOperation("repeat string")
  @ApiResponses(
    ApiResponse(code = 200, message = "Success."),
    ApiResponse(code = 400, message = "Validation of request failed.")
  )
  @PostMapping(path = ["/dummy"])
  fun dummy(@Valid @RequestBody request: DummyRequest) = repeatString(request)

}

fun repeatString(request: DummyRequest) = DummyResponse(request.value.repeat(request.times))

@ApiModel(
  value = "The Dummy Request",
  description = "contains info needed to repeat string n times."
)
data class DummyRequest(
  @ApiModelProperty(value = "value to be repeated", required = true)
  @get: NotBlank
  @get: Size(min = 1, max = 10)
  val value: String,

  @ApiModelProperty(value = "number of times to repeat", required = true)
  @get: Min(1)
  val times: Int
)

@ApiModel(
  value = "The Dummy Response",
  description = "contains the multiplied string value."
)
data class DummyResponse(
  @ApiModelProperty(value = "the multiplied string", required = true)
  @get: NotBlank
  @get: Size(min = 1)
  val value: String
)
