package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.fevent.dto.LoginRequest
import ru.nsu.fevent.dto.LoginResponse
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.service.AuthService
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/authenticate")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(
        response: HttpServletResponse,
        @RequestBody loginRequest: LoginRequest
    ): Response<LoginResponse> {

        val loginResponse = authService.login(loginRequest)

        response.addCookie(Cookie("accessToken", loginResponse.jwtPair.accessToken))
        response.addCookie(Cookie("refreshToken", loginResponse.jwtPair.refreshToken))

        return Response.withData(loginResponse)
    }

    @PostMapping("/saved")
    fun getSaved() : Response<String> {
        return Response.withData("SUCCESS")
    }
}
