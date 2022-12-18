package ru.nsu.fevent.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.AuthException
import java.util.*

object JwtUtils {
    private const val JWT_AUTH_TOKEN_VALIDITY: Long = 600000_000000 // 10 минут
    private const val JWT_REFRESH_TOKEN_VALIDITY: Long = 604800000 // 1 неделя
    private const val SECRET = "secretPhrase"

    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET)

    fun generateTokenPair(user: User): JwtPair {
        val authToken = JWT.create()
            .withSubject(user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_AUTH_TOKEN_VALIDITY))
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY))
            .sign(algorithm)

        return JwtPair(authToken, refreshToken)
    }

    fun parseToken(token: String): DecodedJWT {
        return JWT().decodeJwt(token)
    }

    fun verifyToken(token: String): DecodedJWT {
        return try {
            val verifier: JWTVerifier = JWT.require(algorithm).build()
            verifier.verify(token)
        } catch (exception: JWTVerificationException) {
            throw AuthException("Переданный JWT токен не валиден")
        }
    }

    fun getUserIdByAccessToken(accessToken: String): Int {
        val decodedAccessTokenJwt = parseToken(accessToken)
        return Integer.parseInt(decodedAccessTokenJwt.subject)
    }
}