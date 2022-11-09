package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.repository.EventRepository
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.EventMapper

@Service
class EventService(val eventRepository: EventRepository, val userRepository: UserRepository){
    fun createEvent(eventCreateRequest: EventCreateRequest, creatorId: Int): EventDto{
        val creator = userRepository.getById(creatorId)
        val event = EventMapper.mapEventCreateRequestToEntity(eventCreateRequest, creator)
        val savedEvent = eventRepository.save(event)

        return EventMapper.mapEntityToDto(savedEvent, creatorId)
    }
}