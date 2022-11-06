package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.entity.Event

object EventMapper {
    fun mapEventCreateRequestToEntity(eventCreateRequest: EventCreateRequest): Event =
        Event(
            name = eventCreateRequest.name,
            description = eventCreateRequest.description,
            datetimeStart = eventCreateRequest.datetime_start,
            datetimeEnd = eventCreateRequest.datetime_end,
            address = eventCreateRequest.address,
            maxMembers = eventCreateRequest.max_members,
            ageMin = eventCreateRequest.age_min,
            ageMax = eventCreateRequest.age_max,
            isOnline = eventCreateRequest.is_online,
            isPrivate = eventCreateRequest.is_private,
            creatorId = eventCreateRequest.creatorId
        )

    fun mapEntityToDto(eventEntity: Event): EventDto =
        EventDto(
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
            eventEntity.creatorId
        )
}