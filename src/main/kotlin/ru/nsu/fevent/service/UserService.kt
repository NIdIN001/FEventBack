package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import ru.nsu.fevent.dto.ProfileInfoRequest
import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.RegistrationException
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.PasswordGenerationUtils
import ru.nsu.fevent.utils.UserMapper
import java.time.LocalDateTime
import java.util.regex.Pattern

@Service
class UserService(val userRepository: UserRepository) {

    companion object {
        private val CAPITAL_LETTER_PATTERN = Pattern.compile("[A-ZА-Я]")
        private const val CAPITAL_LETTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 заглавную букву"

        private val DIGIT_PATTERN = Pattern.compile("[0-9]")
        private const val DIGIT_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 цифру"

        private val SPECIAL_CHARACTER_PATTERN = Pattern.compile("[_@\\-!#$%^&*()+{}\"'?><,.~]")
        private const val SPECIAL_CHARACTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 спец. символ"
    }

    fun registerUser(registrationRequest: RegistrationRequest): UserDto {
        validatePassword(registrationRequest.password, registrationRequest.passwordCheck)

        userRepository.getByLogin(registrationRequest.login)
            ?.let { throw RegistrationException("Пользователь с логином ${registrationRequest.login} уже существует") }

        userRepository.getByPhoneNumber(registrationRequest.phoneNumber)
            ?.let { throw RegistrationException("Пользователь с номером телефона ${registrationRequest.phoneNumber} уже существует") }

        val salt = PasswordGenerationUtils.generateSalt()
        val saltBase64 = Base64Utils.encodeToString(salt)
        val hashPassword = PasswordGenerationUtils.hashPassword(registrationRequest.password, salt)
        val hashPasswordBase64 = Base64Utils.encodeToString(hashPassword)

        val user = UserMapper.mapRegistrationRequestToEntity(registrationRequest, saltBase64, hashPasswordBase64)
        user.createdAt = LocalDateTime.now()

        val savedUser = userRepository.save(user)

        return UserMapper.mapEntityToDto(savedUser)
    }

    private fun validatePassword(password: String, passwordCheck: String) {
        if (password != passwordCheck) {
            throw RegistrationException("Пароли должны совпадать")
        }

        checkRegex(password, CAPITAL_LETTER_PATTERN, CAPITAL_LETTER_ERROR_MESSAGE)
        checkRegex(password, DIGIT_PATTERN, DIGIT_ERROR_MESSAGE)
        checkRegex(password, SPECIAL_CHARACTER_PATTERN, SPECIAL_CHARACTER_ERROR_MESSAGE)
    }

    private fun checkRegex(password: String, regex: Pattern, errorMessage: String) {
        if (!regex.matcher(password).find()) {
            throw RegistrationException(errorMessage)
        }
    }

    private fun findUserByAccessToken(accessToken: String): User {
        val decodedAccessTokenJwt = JwtUtils.verifyToken(accessToken)
        println(Integer.parseInt(decodedAccessTokenJwt.subject))
        val userOptional =  userRepository.findById(Integer.parseInt(decodedAccessTokenJwt.subject))
        if (userOptional.isEmpty) {
            println("12434345")
            throw UserNotFoundException("Пользователь не найден")
        }
        return userOptional.get()
    }

    fun getUserPersonalInfo(accessToken: String): UserDto {
        return UserMapper.mapEntityToDto(findUserByAccessToken(accessToken))
    }

    fun changeUserPersonalInfo(accessToken: String, request: ProfileInfoRequest) : UserDto {
        val user = findUserByAccessToken(accessToken)
        user.city = request.city
        user.login = request.login
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.phoneNumber = request.phoneNumber
        userRepository.save(user)
        return UserMapper.mapEntityToDto(user)
    }
}