package ru.nsu.fevent.dto

import java.time.LocalDateTime
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class EventCreateRequest (
    @field:NotBlank(message = "Поле \"Название\" не должно быть пустым")
    @field:Size(max = 128, message = "Длина названия должна быть до 128 символов")
    val name: String,

    val description: String?,

    val category: String,

    @field:NotNull(message = "Поле \"Начало\" не должно быть пустым")
    val datetimeStart: LocalDateTime,

    @field:NotNull(message = "Поле \"Окончание\" не должно быть пустым")
    val datetimeEnd: LocalDateTime,

    @field:NotNull(message = "Поле \"Широта\" не должно быть пустым")
    val latitude: Float,

    @field:NotNull(message = "Поле \"Долгота\" не должно быть пустым")
    val longitude: Float,

    @Min(1)
    val maxMembers: Int?,

    @Min(0)
    val ageMin: Int?,

    @Min(0)
    val ageMax: Int?,

    @field:NotNull(message = "Поле \"Формат проведения\" не должно быть пустым")
    val isOnline: Boolean,

    @field:NotNull(message = "Поле \"Приватность\" не должно быть пустым")
    val isPrivate: Boolean
)