package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.*
import ru.nsu.fevent.entity.Event
import ru.nsu.fevent.entity.User

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

    fun mapEntityToFoundDto(searchedEvents: Event): EventFoundDto =
        EventFoundDto(
            searchedEvents.id,
            searchedEvents.name,
            searchedEvents.datetimeStart.toString(),
            searchedEvents.datetimeEnd.toString(),
            searchedEvents.latitude,
            searchedEvents.longitude,
            searchedEvents.maxMembers
        )

    fun mapFoundDtoToViewDto(foundsDto: List<EventFoundDto>, pageCount: Int): EventViewDto =
        EventViewDto(
            foundsDto,
            pageCount
        )
}