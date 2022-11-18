package ru.nsu.fevent.dto

import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class EventCreateRequest (
    @field:NotBlank(message = "Поле \"Название\" не должно быть пустым")
    @field:Size(max = 128, message = "Длина названия должна быть до 128 символов")
    val name: String,

    val description: String?,

    @field:NotBlank(message = "Поле \"Начало\" не должно быть пустым")
    val datetimeStart: LocalDateTime,

    @field:NotBlank(message = "Поле \"Окончание\" не должно быть пустым")
    val datetimeEnd: LocalDateTime,

    @field:NotBlank(message = "Поле \"Широта\" не должно быть пустым")
    val latitude: Float,

    @field:NotBlank(message = "Поле \"Долгота\" не должно быть пустым")
    val longitude: Float,

    val maxMembers: Int?,

    val ageMin: Int?,

    val ageMax: Int?,

    @field:NotBlank(message = "Поле \"Формат проведения\" не должно быть пустым")
    val isOnline: Boolean,

    @field:NotBlank(message = "Поле \"Приватность\" не должно быть пустым")
    val isPrivate: Boolean
)