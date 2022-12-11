package ru.nsu.fevent.dto

data class UserDto(
    val id: Int,
    val login: String,
    val firstName: String,
    val lastName: String,
    val city: String?,
    val email: String?,
    val phoneNumber: String?,
    val createdAt: String
)
