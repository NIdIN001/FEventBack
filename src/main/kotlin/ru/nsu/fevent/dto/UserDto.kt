package ru.nsu.fevent.dto

data class UserDto(
    val login: String,
    val firstname: String,
    val lastName: String,
    val city: String?,
    val email: String?,
    val createdAt: String
)
