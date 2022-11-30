package ru.nsu.fevent.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.*
import ru.nsu.fevent.repository.EventRepository
import ru.nsu.fevent.repository.UserRepository
import ru.nsu.fevent.utils.EventMapper
import ru.nsu.fevent.utils.UserMapper

@Service
class EventService(val eventRepository: EventRepository, val userRepository: UserRepository){
    fun createEvent(eventCreateRequest: EventCreateRequest, creatorId: Int): EventDto{
        val creator = userRepository.getById(creatorId)
        val event = EventMapper.mapEventCreateRequestToEntity(eventCreateRequest, creator)
        val savedEvent = eventRepository.save(event)

        return EventMapper.mapEntityToDto(savedEvent, UserMapper.mapEntityToDto(creator))
    }

    fun viewEvents(substring: String, page: Int, pagesize: Int): EventViewDto {
        val foundEvents = eventRepository.searchByName(
            substring,
            PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "name"))
        )
            .map { event -> EventMapper.mapEntityToFoundDto(event) }

        val pageCount = if (foundEvents.size % pagesize == 0) foundEvents.size/pagesize else foundEvents.size/pagesize + 1

        return EventMapper.mapFoundDtoToViewDto(foundEvents, pageCount)
    }

    fun findEventById(eventRequest: EventRequest): EventDto{
        val event = eventRepository.getById(eventRequest.eventId)

        return EventMapper.mapEntityToDto(event, UserMapper.mapEntityToDto(event.creator))
    }
}