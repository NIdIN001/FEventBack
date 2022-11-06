package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.repository.EventRepository
import ru.nsu.fevent.utils.EventMapper

@Service
class EventService(val eventRepository: EventRepository){
    fun createEvent(eventCreateRequest: EventCreateRequest): EventDto{
        val event = EventMapper.mapEventCreateRequestToEntity(eventCreateRequest)
        val savedEvent = eventRepository.save(event)

        return EventMapper.mapEntityToDto(savedEvent)
    }
}