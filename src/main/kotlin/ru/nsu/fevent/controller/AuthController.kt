package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.LoginRequest
import ru.nsu.fevent.dto.LoginResponse
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.service.AuthService
import ru.nsu.fevent.utils.CookieUtils
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
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
        CookieUtils.addAuthCookies(response, loginResponse.jwtPair.accessToken, loginResponse.jwtPair.refreshToken)

        return Response.withData(loginResponse)
    }

    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @CookieValue(name = "accessToken") accessToken: String
    ): Response<String> {
        deleteAuthCookies(response)

        return Response.withData("SUCCESS")
    }

    private fun deleteAuthCookies(response: HttpServletResponse) {
        val accessTokenCookie = Cookie("accessToken", "")
        accessTokenCookie.maxAge = 0
        accessTokenCookie.path = "/"
        val refreshTokenCookie = Cookie("refreshToken", "")
        refreshTokenCookie.maxAge = 0
        refreshTokenCookie.path = "/"

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
    }
}
