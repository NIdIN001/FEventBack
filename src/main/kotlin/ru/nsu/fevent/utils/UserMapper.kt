package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.entity.User

object UserMapper {

    fun mapRegistrationRequestToEntity(registrationRequest: RegistrationRequest, salt: String, password: String): User =
        User(
            email = registrationRequest.email,
            login = registrationRequest.login,
            salt = salt,
            password = password,
            firstName = registrationRequest.firstName,
            lastName = registrationRequest.lastName,
            city = registrationRequest.city,
            phoneNumber = registrationRequest.phoneNumber
        )

    fun mapEntityToDto(userEntity: User): UserDto =
        UserDto(
            userEntity.id,
            userEntity.login,
            userEntity.firstName,
            userEntity.lastName,
            userEntity.city,
            userEntity.email,
            userEntity.phoneNumber,
            userEntity.createdAt.toString()
        )
}