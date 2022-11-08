package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.entity.Event
import ru.nsu.fevent.entity.User

object EventMapper {
    fun mapEventCreateRequestToEntity(eventCreateRequest: EventCreateRequest, creatorId: User): Event =
        Event(
            name = eventCreateRequest.name,
            description = eventCreateRequest.description,
            datetimeStart = eventCreateRequest.datetimeStart,
            datetimeEnd = eventCreateRequest.datetimeEnd,
            address = eventCreateRequest.address,
            maxMembers = eventCreateRequest.maxMembers,
            ageMin = eventCreateRequest.ageMin,
            ageMax = eventCreateRequest.ageMax,
            isOnline = eventCreateRequest.isOnline,
            isPrivate = eventCreateRequest.isPrivate,
            creatorId = creatorId
        )

    fun mapEntityToDto(eventEntity: Event, creatorId: Int): EventDto =
        EventDto(
            eventEntity.id,
            eventEntity.name,
            eventEntity.description,
            eventEntity.datetimeStart.toString(),
            eventEntity.datetimeEnd.toString(),
            eventEntity.address,
            eventEntity.maxMembers,
            eventEntity.ageMin,
            eventEntity.ageMax,
            eventEntity.isOnline,
            eventEntity.isPrivate,
            creatorId
        )
}