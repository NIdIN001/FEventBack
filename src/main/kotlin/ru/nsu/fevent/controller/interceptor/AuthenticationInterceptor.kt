package ru.nsu.fevent.controller.interceptor

import com.auth0.jwt.exceptions.JWTVerificationException
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.AuthException
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.CookieUtils
import ru.nsu.fevent.utils.JwtUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Перехватчик, проверяющий, что в cookie переданного запроса содержится JWT с информацией о пользователе
 * для доступа к защищенному ресурсу, если пользоватей не передал нужные данные, то доступ запрещен с ошибкой
 * AuthException. Список защищенных ресусров формируется в MvcConfiguration.
 */
@Component
class AuthenticationInterceptor(private val userRepository: UserRepository) : HandlerInterceptor {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val cookiesArray = request.cookies ?: throw AuthException("Пользователь не авторизован")

        val accessTokenCookie = Arrays.stream(cookiesArray)
            .filter { c -> c.name == "accessToken" }
            .findFirst()
            .orElseThrow { AuthException("Отсутствует обязательный cookie \"accessToken\"") }

        val refreshTokenCookie = Arrays.stream(cookiesArray)
            .filter { c -> c.name == "refreshToken" }
            .findFirst()
            .orElseThrow { AuthException("Отсутствует обязательный cookie \"refreshToken\"") }

        val decodedAccessTokenJwt = JwtUtils.parseToken(accessTokenCookie.value)
        if (getNow().after(decodedAccessTokenJwt.expiresAt)) {
            logger.info { "Access токен пользователя id=${decodedAccessTokenJwt.subject} просрочен" }
            val userOptional = userRepository.findById(Integer.parseInt(decodedAccessTokenJwt.subject))
            if (userOptional.isEmpty) {
                return false
            }

            val user = userOptional.get()

            if (user.refreshToken != refreshTokenCookie.value) {
                return false
            } else {
                updateJwtTokenPair(user, response)
            }
        } else {
            try {
                JwtUtils.verifyToken(accessTokenCookie.value)
            } catch (e: JWTVerificationException) {
                throw AuthException("Переданный JWT токен не валиден")
            }
        }

        return true
    }

    private fun updateJwtTokenPair(user: User, response: HttpServletResponse) {
        val jwtPair = JwtUtils.generateTokenPair(user)
        user.refreshToken = jwtPair.refreshToken
        userRepository.save(user)

        CookieUtils.addAuthCookies(response, jwtPair.accessToken, jwtPair.refreshToken)
        logger.info { "Обновлены JWT токены пользователя id=${user.id}" }
    }

    private fun getNow(): Date {
        return Date(System.currentTimeMillis())
    }
}
