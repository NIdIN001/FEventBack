package ru.nsu.fevent.utils

import org.springframework.util.Base64Utils
import ru.nsu.fevent.exception.PasswordException
import java.util.regex.Pattern

object PasswordUtils {

    private val CAPITAL_LETTER_PATTERN = Pattern.compile("[A-ZА-Я]")
    private const val CAPITAL_LETTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 заглавную букву"

    private val DIGIT_PATTERN = Pattern.compile("[0-9]")
    private const val DIGIT_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 цифру"

    private val SPECIAL_CHARACTER_PATTERN = Pattern.compile("[_@\\-!#$%^&*()+{}\"'?><,.~]")
    private const val SPECIAL_CHARACTER_ERROR_MESSAGE = "Пароль должен содержать как минимум 1 спец. символ"

    fun validatePassword(password: String, passwordCheck: String) {
        if (password != passwordCheck) {
            throw PasswordException("Пароли должны совпадать")
        }

        checkRegex(password, CAPITAL_LETTER_PATTERN, CAPITAL_LETTER_ERROR_MESSAGE)
        checkRegex(password, DIGIT_PATTERN, DIGIT_ERROR_MESSAGE)
        checkRegex(password, SPECIAL_CHARACTER_PATTERN, SPECIAL_CHARACTER_ERROR_MESSAGE)
    }

    fun checkPassword(enteredPassword: String, password: String, salt: String) {
        val hashedPassword = PasswordGenerationUtils
            .hashPassword(enteredPassword, Base64Utils.decodeFromString(salt))

        if (password != Base64Utils.encodeToString(hashedPassword)) {
            throw PasswordException("Неверный пароль")
        }
    }

    private fun checkRegex(password: String, regex: Pattern, errorMessage: String) {
        if (!regex.matcher(password).find()) {
            throw PasswordException(errorMessage)
        }
    }
}