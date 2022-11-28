package ru.nsu.fevent.dto

data class EventViewDto (
    val id: Int,
    val name: String,
    val datetimeStart: String,
    val datetimeEnd: String,
    val latitude: Float,
    val longitude: Float,
    val maxMembers: Int?
)