package ru.nsu.fevent.dto

data class EventDto(
    val name: String,
    val description: String?,
    val datetimeStart: String,
    val datetimeEnd: String?,
    val address: String,
    val maxMembers: Int?,
    val ageMin: Int?,
    val ageMax: Int?,
    val isOnline: Boolean,
    val isPrivate: Boolean,
    val creatorId: Int?
)
