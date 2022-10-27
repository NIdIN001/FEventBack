package ru.nsu.fevent.utils

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

object CookieUtils {
    fun addAuthCookies(response: HttpServletResponse, accessToken: String, refreshToken: String) {
        val accessTokenCookie = Cookie("accessToken", accessToken)
        accessTokenCookie.maxAge = 365 * 24 * 60 * 60
        accessTokenCookie.path = "/"
        val refreshTokenCookie = Cookie("refreshToken", refreshToken)
        refreshTokenCookie.maxAge = 365 * 24 * 60 * 60
        refreshTokenCookie.path = "/"

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
    }
}