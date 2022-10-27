package ru.nsu.fevent.utils

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

object CookieUtils {
    fun addAuthCookies(response: HttpServletResponse, accessToken: String, refreshToken: String) {
        response.addCookie(createAccessTokenCookie(365 * 24 * 60 * 60, accessToken))
        response.addCookie(createRefreshTokenCookie(365 * 24 * 60 * 60, refreshToken))
    }

    fun deleteAuthCookies(response: HttpServletResponse) {
        response.addCookie(createAccessTokenCookie(0, ""))
        response.addCookie(createRefreshTokenCookie(0, ""))
    }

    private fun createAccessTokenCookie(maxAge: Int, value: String): Cookie {
        val accessTokenCookie = Cookie("accessToken", value)
        accessTokenCookie.maxAge = maxAge
        accessTokenCookie.path = "/"
        return accessTokenCookie
    }

    private fun createRefreshTokenCookie(maxAge: Int, value: String): Cookie {
        val refreshTokenCookie = Cookie("refreshToken", value)
        refreshTokenCookie.maxAge = maxAge
        refreshTokenCookie.path = "/"
        return refreshTokenCookie
    }
}