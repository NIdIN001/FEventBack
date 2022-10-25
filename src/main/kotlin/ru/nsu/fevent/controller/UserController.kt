package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.ProfileInfoRequest
import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.service.UserService
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody @Valid registrationRequest: RegistrationRequest): Response<UserDto> {
        val registeredUser = userService.registerUser(registrationRequest)
        return Response.withData(registeredUser)
    }

    @GetMapping("/profile-info")
    fun getProfileInfo(@CookieValue(name = "accessToken") accessToken: String): Response<UserDto> {
        val user = userService.getUserPersonalInfo(accessToken)
        return Response.withData(user)
    }

    @PutMapping("/change-profile-info")
    fun changeProfileInfo(@CookieValue(name = "accessToken") accessToken: String,
                          @RequestBody @Valid request: ProfileInfoRequest): Response<UserDto> {
        val updatedUser = userService.changeUserPersonalInfo(accessToken, request)
        return Response.withData(updatedUser)
    }
}