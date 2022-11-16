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
        val login = creator.login
        val firstname = creator.firstName
        val lastname = creator.lastName
        val city = creator.city
        val email = creator.email
        val phoneNumber = creator.phoneNumber
        val createdAt = creator.createdAt
        val event = EventMapper.mapEventCreateRequestToEntity(eventCreateRequest, creator)
        val savedEvent = eventRepository.save(event)

        return EventMapper.mapEntityToDto(savedEvent, login, firstname, lastname, city, email, phoneNumber, createdAt)
    }
}