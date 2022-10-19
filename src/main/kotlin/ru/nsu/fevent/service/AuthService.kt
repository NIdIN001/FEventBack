package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import ru.nsu.fevent.dto.LoginRequest
import ru.nsu.fevent.dto.LoginResponse
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.AuthException
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.PasswordGenerationUtils
import ru.nsu.fevent.utils.UserMapper

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    companion object {
        private const val AUTH_EXCEPTION_MESSAGE = "Неверный логин или пароль"
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val user = checkUser(loginRequest)

        val jwtPair = JwtUtils.generateTokenPair(user)
        user.refreshToken = jwtPair.refreshToken

        val savedUser = userRepository.save(user)

        return LoginResponse(jwtPair, UserMapper.mapEntityToDto(savedUser))
    }

    private fun checkUser(loginRequest: LoginRequest): User {
        val user = userRepository.getByLogin(loginRequest.login) ?: throw AuthException(AUTH_EXCEPTION_MESSAGE)

        val hashedPassword = PasswordGenerationUtils
            .hashPassword(loginRequest.password, Base64Utils.decodeFromString(user.salt))

        if (user.password != Base64Utils.encodeToString(hashedPassword)) {
            throw AuthException(AUTH_EXCEPTION_MESSAGE)
        }

        return user
    }
}