package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.*
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

    @PutMapping("/change-password")
    fun changePassword(@CookieValue(name = "accessToken") accessToken: String,
                       @RequestBody @Valid request: ChangePasswordRequest): Response<UserDto> {
        val updatedUser = userService.changePassword(accessToken, request)
        return Response.withData(updatedUser)
    }
}