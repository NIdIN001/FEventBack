package ru.nsu.fevent.dto

data class EventDto(
    val id: Int,
    val name: String,
    val description: String?,
    val datetimeStart: String,
    val datetimeEnd: String,
    val address: String,
    val maxMembers: Int?,
    val ageMin: Int?,
    val ageMax: Int?,
    val isOnline: Boolean,
    val isPrivate: Boolean,
    val login: String,
    val firstname: String,
    val lastName: String,
    val city: String?,
    val email: String?,
    val phoneNumber: String?,
    val createdAt: String
)