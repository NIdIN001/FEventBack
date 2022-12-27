package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.*
import ru.nsu.fevent.entity.Event
import ru.nsu.fevent.entity.Members
import ru.nsu.fevent.entity.User
import java.time.format.DateTimeFormatter

object EventMapper {
    fun mapEventCreateRequestToEntity(eventCreateRequest: EventCreateRequest, creator: User): Event =
        Event(
            name = eventCreateRequest.name,
            description = eventCreateRequest.description,
            category = eventCreateRequest.category,
            datetimeStart = eventCreateRequest.datetimeStart,
            datetimeEnd = eventCreateRequest.datetimeEnd,
            latitude = eventCreateRequest.latitude,
            longitude = eventCreateRequest.longitude,
            maxMembers = eventCreateRequest.maxMembers,
            membersCount = 0,
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
            eventEntity.category,
            eventEntity.datetimeStart.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            eventEntity.datetimeEnd.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            eventEntity.latitude,
            eventEntity.longitude,
            eventEntity.maxMembers,
            eventEntity.membersCount,
            eventEntity.ageMin,
            eventEntity.ageMax,
            eventEntity.isOnline,
            eventEntity.isPrivate,
            userDto
        )

    fun mapFoundDtoToViewDto(foundsDto: List<EventDto>, pageCount: Int): EventViewDto =
        EventViewDto(
            foundsDto,
            pageCount
        )

    fun mapEntityToEventWithDistanceDto(event: Event, distance: Int): EventWithDistanceDto {
        val creatorDto = UserMapper.mapEntityToDto(event.creator)
        val eventDto = mapEntityToDto(event, creatorDto)
        return EventWithDistanceDto(eventDto, distance)
    }

    fun mapToMembers(userId: User, eventId: Event): Members =
        Members(
            user = userId,
            event = eventId
        )
}