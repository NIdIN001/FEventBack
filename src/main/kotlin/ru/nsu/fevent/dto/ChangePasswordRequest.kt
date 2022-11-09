package ru.nsu.fevent.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ChangePasswordRequest(
    val oldPassword: String,

    @field:NotBlank(message = "Поле \"Пароль\" не должно быть пустым")
    @field:Size(min = 8, max = 64, message = "Длина пароля должна быть от 8 до 64 символов")
    val newPassword: String,

    @field:NotBlank(message = "Поле \"Подтверждение пароля\" не должно быть пустым")
    @field:Size(min = 8, max = 64, message = "Длина пароля должна быть от 8 до 64 символов")
    val newPasswordCheck: String,
)
