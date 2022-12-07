package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.ChangePasswordRequest
import ru.nsu.fevent.dto.ProfileInfoRequest
import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.JoinException
import ru.nsu.fevent.exception.RegistrationException
import ru.nsu.fevent.exception.UserNotFoundException
import ru.nsu.fevent.repository.EventRepository
import ru.nsu.fevent.repository.MembersRepository
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.*
import java.time.LocalDateTime

@Service
class UserService(val userRepository: UserRepository, val eventRepository: EventRepository, val membersRepository: MembersRepository) {

    fun registerUser(registrationRequest: RegistrationRequest): UserDto {
        PasswordUtils.checkPasswordConstraints(registrationRequest.password, registrationRequest.passwordCheck)

        userRepository.getByLogin(registrationRequest.login)
            ?.let { throw RegistrationException("Пользователь с логином ${registrationRequest.login} уже существует") }

        userRepository.getByPhoneNumber(registrationRequest.phoneNumber)
            ?.let { throw RegistrationException("Пользователь с номером телефона ${registrationRequest.phoneNumber} уже существует") }

        val salt = PasswordGenerationUtils.generateSalt()
        val hashedPassword = PasswordGenerationUtils
            .hashPassword(registrationRequest.password, salt)

        val user = UserMapper
            .mapRegistrationRequestToEntity(registrationRequest, salt, hashedPassword)
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

        PasswordUtils.checkEnteredPasswordMatchesUserPassword(request.oldPassword, user.password, user.salt)

        PasswordUtils.checkPasswordConstraints(request.newPassword, request.newPasswordCheck)

        val salt = PasswordGenerationUtils.generateSalt()
        val hashedPassword = PasswordGenerationUtils
            .hashPassword(request.newPassword, salt)

        user.salt = salt
        user.password = hashedPassword

        val savedUser = userRepository.save(user)
        return UserMapper.mapEntityToDto(savedUser)
    }

    fun joinToEvent(userId: Int, eventId: Int): String {
        val event = eventRepository.getById(eventId)
        if (event.maxMembers == null || event.membersCount < event.maxMembers!!) {
            val user = userRepository.getById(userId)
            event.membersCount += 1
            val members = EventMapper.mapToMembers(user, event)
            membersRepository.save(members)
            return ("SUCCESS")
        } else throw JoinException("Достигнуто максимальное количество человек")
    }
}