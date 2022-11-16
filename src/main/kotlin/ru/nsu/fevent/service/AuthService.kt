package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.LoginRequest
import ru.nsu.fevent.dto.LoginResponse
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.AuthException
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.PasswordUtils
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

        PasswordUtils.checkEnteredPasswordMatchesUserPassword(loginRequest.password, user.password, user.salt)

        return user
    }
}