package ru.nsu.fevent.dto

import javax.validation.constraints.Size

data class PersonalDataRequest(
    @field:Size(max = 128, message = "Длина поля \"Имя\" не может превышать 128 символов")
    val firstName: String?,

    @field:Size(max = 128, message = "Длина поля \"Фамилия\" не может превышать 128 символов")
    val lastName: String?,

    @field:Size(max = 128, message = "Длина поля \"Город\" не может превышать 128 символов")
    val city: String?
)
