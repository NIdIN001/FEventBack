package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.service.EventService
import javax.validation.Valid

@RestController
@RequestMapping("/event")
class EventController(val eventService: EventService) {
    @PostMapping("/create")
    fun create(@RequestBody @Valid eventCreateRequest: EventCreateRequest) : Response<EventDto> {
        val createdEvent = eventService.createEvent(eventCreateRequest)
        return Response.withData(createdEvent)
    }
}
