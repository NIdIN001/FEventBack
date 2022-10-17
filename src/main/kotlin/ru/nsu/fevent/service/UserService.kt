package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.PersonalDataRequest
import ru.nsu.fevent.dto.RegistrationRequest
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.exception.RegistrationException
import java.util.regex.Pattern

@Service
class UserService {

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

        /* todo сделать сохраниение нового юзера в БД и возврат сохраненного пользователя из метода */
        return UserDto(null, null, null, registrationRequest.email)
    }

    fun addPersonalInfo(personalDataRequest: PersonalDataRequest): UserDto {

        /* todo сделать изменение персональных данный пользователя и возврат сохраненного пользователя из метода*/
        return UserDto(
            personalDataRequest.firstName,
            personalDataRequest.lastName,
            personalDataRequest.city,
            "test@mail.ru"
        )
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
}