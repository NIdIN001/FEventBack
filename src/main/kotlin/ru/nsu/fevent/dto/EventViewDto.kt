package ru.nsu.fevent.dto

data class EventViewDto(
    val eventDto: List<EventDto>,
    val pageCount: Int
)