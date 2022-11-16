package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.EventCreateRequest
import ru.nsu.fevent.dto.EventDto
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.service.EventService
import ru.nsu.fevent.utils.JwtUtils
import javax.validation.Valid

@RestController
@RequestMapping("/event")
class EventController(val eventService: EventService) {
    @PostMapping("/create")
    fun create(
        @CookieValue(name = "accessToken") accessToken: String,
        @RequestBody @Valid eventCreateRequest: EventCreateRequest
    ) : Response<EventDto> {
        val creatorId = JwtUtils.getUserIdByAccessToken(accessToken)
        val createdEvent = eventService.createEvent(eventCreateRequest, creatorId)
        return Response.withData(createdEvent)
    }
}