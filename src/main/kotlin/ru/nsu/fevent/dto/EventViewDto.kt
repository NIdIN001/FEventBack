package ru.nsu.fevent.dto

data class EventViewDto(
    val eventFoundDto: List<EventFoundDto>,
    val pageCount: Int
)