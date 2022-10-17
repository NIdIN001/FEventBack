package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.fevent.dto.PersonalDataRequest
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

    @PostMapping("/person-info")
    fun addPersonalInfo(@RequestBody @Valid personalDataRequest: PersonalDataRequest): Response<UserDto> {
        val registeredUser = userService.addPersonalInfo(personalDataRequest)
        return Response.withData(registeredUser)
    }
}