package ru.nsu.fevent.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.dto.EventRequest
import ru.nsu.fevent.dto.EventViewDto
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

    fun viewEvents(s: String, sort: String, page: Int): List<EventViewDto>{
        var direction = Sort.by(Sort.Direction.ASC, "name")
        if (sort == "desc"){
            direction = Sort.by(Sort.Direction.DESC, "name")
        }

        val perPage = 10
        val searchedEvents = eventRepository.searchByName(s, PageRequest.of(page - 1, perPage, direction))

        var viewEvents = ArrayList<EventViewDto>()
        for (i in searchedEvents.indices) {
            viewEvents += EventMapper.mapEntityToViewDto(searchedEvents[i])
        }

        return viewEvents
    }

    fun chooseEvent(eventRequest: EventRequest): EventDto{
        val event = eventRepository.getById(eventRequest.eventId)

        return EventMapper.mapEntityToDto(event, UserMapper.mapEntityToDto(event.creator))
    }
}