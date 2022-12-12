package ru.nsu.fevent.dto

data class EventDto(
    val id: Int,
    val name: String,
    val description: String?,
    val datetimeStart: String,
    val datetimeEnd: String,
    val latitude: Float,
    val longitude: Float,
    val maxMembers: Int?,
    val membersCount: Int,
    val ageMin: Int?,
    val ageMax: Int?,
    val isOnline: Boolean,
    val isPrivate: Boolean,
    val userDto: UserDto
)
