package ru.nsu.fevent.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.*
import ru.nsu.fevent.entity.Event
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

    fun filterEvents(foundEvents: List<Event>, name: String?, isOnline: Boolean?, ageMin: Int?, ageMax: Int?, category: String?): List<Event> {
        var filteredEvents = foundEvents
        if (name != "") {
            filteredEvents = foundEvents.filter { it.name.contains(name!!, ignoreCase = true) }
        }
        if (isOnline != null) {
            filteredEvents = foundEvents.filter { it.isOnline == isOnline }
        }
        if (ageMin != null) {
            filteredEvents = filteredEvents.filter { it.ageMin == null || it.ageMin!! <= ageMin }
        }

        if (ageMax != null) {
            filteredEvents = filteredEvents.filter { it.ageMax == null || it.ageMax!! >= ageMax }
        }

        if (category != "") {
            filteredEvents = filteredEvents.filter { it.category == category }
        }

        return filteredEvents
    }

    fun viewEvents(name: String?, page: Int, pagesize: Int, isOnline: Boolean?, ageMin: Int?, ageMax: Int?, category: String?): EventViewDto {
        val foundEvents = eventRepository.findAll(
            PageRequest.of(page - 1, pagesize, Sort.by(Sort.Direction.ASC, "name"))
        )
        val filteredEvents = filterEvents(foundEvents, name, isOnline, ageMin, ageMax, category)
            .map { event -> EventMapper.mapEntityToDto(event, UserMapper.mapEntityToDto(event.creator)) }

        val pageCount = if (filteredEvents.size % pagesize == 0) filteredEvents.size/pagesize else filteredEvents.size/pagesize + 1

        return EventMapper.mapFoundDtoToViewDto(filteredEvents, pageCount)
    }

    fun findEventById(eventId: Int): EventDto{
        val event = eventRepository.getById(eventId)

        return EventMapper.mapEntityToDto(event, UserMapper.mapEntityToDto(event.creator))
    }
}