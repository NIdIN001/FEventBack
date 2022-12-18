package ru.nsu.fevent.dto

data class EventWithDistanceDto(
    val eventDto: EventDto,
    val distanceFromUserInMetres: Int
)
