package ru.nsu.fevent.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ProfileInfoRequest(

    @field:NotBlank(message = "Поле \"Логин\" не должно быть пустым")
    @field:Size(max = 64, message = "Длина логина должна быть до 64 символов")
    val login: String,

    @field:NotBlank(message = "Поле \"Имя\" не должно быть пустым")
    @field:Size(max = 128, message = "Длина поля \"Имя\" не может превышать 128 символов")
    val firstName: String,

    @field:NotBlank(message = "Поле \"Фамилия\" не должно быть пустым")
    @field:Size(max = 128, message = "Длина поля \"Фамилия\" не может превышать 128 символов")
    val lastName: String,

    @field:Size(max = 128, message = "Длина поля \"Город\" не может превышать 128 символов")
    val city: String?,

    @field:NotBlank(message = "Поле \"Мобильный телефон\" не должно быть пустым")
    @field:Size(min = 11, max = 11, message = "Длина поля \"Мобильный телефон\" должна быть 11 символов")
    val phoneNumber: String
)
