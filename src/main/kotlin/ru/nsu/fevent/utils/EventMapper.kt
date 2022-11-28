package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.dto.EventViewDto
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.entity.Event
import ru.nsu.fevent.entity.User
import java.time.LocalDateTime

object EventMapper {
    fun mapEventCreateRequestToEntity(eventCreateRequest: EventCreateRequest, creator: User): Event =
        Event(
            name = eventCreateRequest.name,
            description = eventCreateRequest.description,
            datetimeStart = eventCreateRequest.datetimeStart,
            datetimeEnd = eventCreateRequest.datetimeEnd,
            latitude = eventCreateRequest.latitude,
            longitude = eventCreateRequest.longitude,
            maxMembers = eventCreateRequest.maxMembers,
            ageMin = eventCreateRequest.ageMin,
            ageMax = eventCreateRequest.ageMax,
            isOnline = eventCreateRequest.isOnline,
            isPrivate = eventCreateRequest.isPrivate,
            creator = creator
        )

    fun mapEntityToDto(eventEntity: Event, userDto: UserDto): EventDto =
        EventDto(
            eventEntity.id,
            eventEntity.name,
            eventEntity.description,
            eventEntity.datetimeStart.toString(),
            eventEntity.datetimeEnd.toString(),
            eventEntity.latitude,
            eventEntity.longitude,
            eventEntity.maxMembers,
            eventEntity.ageMin,
            eventEntity.ageMax,
            eventEntity.isOnline,
            eventEntity.isPrivate,
            userDto
        )

    fun mapEntityToViewDto(searchedEvents: Event): EventViewDto =
        EventViewDto(
            searchedEvents.id,
            searchedEvents.name,
            searchedEvents.datetimeStart.toString(),
            searchedEvents.datetimeEnd.toString(),
            searchedEvents.latitude,
            searchedEvents.longitude,
            searchedEvents.maxMembers
        )
}