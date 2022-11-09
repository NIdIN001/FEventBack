package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import ru.nsu.fevent.dto.ChangePasswordRequest
import ru.nsu.fevent.dto.ProfileInfoRequest
import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.RegistrationException
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.PasswordGenerationUtils
import ru.nsu.fevent.utils.PasswordUtils
import ru.nsu.fevent.utils.UserMapper
import java.time.LocalDateTime

@Service
class UserService(val userRepository: UserRepository) {

    fun registerUser(registrationRequest: RegistrationRequest): UserDto {
        PasswordUtils.validatePassword(registrationRequest.password, registrationRequest.passwordCheck)

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

    fun getUserPersonalInfo(accessToken: String): UserDto {
        val user = findUserById(JwtUtils.getUserIdByAccessToken(accessToken))
        return UserMapper.mapEntityToDto(user)
    }

    fun changeUserPersonalInfo(accessToken: String, request: ProfileInfoRequest) : UserDto {
        val user = findUserById(JwtUtils.getUserIdByAccessToken(accessToken))

        user.city = request.city
        user.login = request.login
        user.firstName = request.firstName
        user.lastName = request.lastName
        user.phoneNumber = request.phoneNumber
        val savedUser = userRepository.save(user)
        return UserMapper.mapEntityToDto(savedUser)
    }

    private fun findUserById(id: Int): User {
        val userOptional = userRepository.findById(id)
        if(userOptional.isEmpty) {
            throw UserNotFoundException("Пользователь не найден")
        }
        return userOptional.get()
    }

    fun changePassword(accessToken: String, request: ChangePasswordRequest): UserDto {
        val user = findUserById(JwtUtils.getUserIdByAccessToken(accessToken))

        PasswordUtils.checkPassword(request.oldPassword, user.password, user.salt)

        PasswordUtils.validatePassword(request.newPassword, request.newPasswordCheck)

        val salt = Base64Utils.decodeFromString(user.salt)
        val hashedPassword = PasswordGenerationUtils.hashPassword(request.newPassword, salt)

        user.password = Base64Utils.encodeToString(hashedPassword)

        val savedUser = userRepository.save(user)
        return UserMapper.mapEntityToDto(savedUser)
    }
}