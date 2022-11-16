package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
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
            address = eventCreateRequest.address,
            maxMembers = eventCreateRequest.maxMembers,
            ageMin = eventCreateRequest.ageMin,
            ageMax = eventCreateRequest.ageMax,
            isOnline = eventCreateRequest.isOnline,
            isPrivate = eventCreateRequest.isPrivate,
            creator = creator
        )

    fun mapEntityToDto(eventEntity: Event,
                       login: String,
                       firstname: String,
                       lastName: String,
                       city: String?,
                       email: String?,
                       phoneNumber: String?,
                       createdAt: LocalDateTime?): EventDto =
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
            login,
            firstname,
            lastName,
            city,
            email,
            phoneNumber,
            createdAt.toString()
        )
}