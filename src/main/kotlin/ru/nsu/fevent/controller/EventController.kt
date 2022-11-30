package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.*
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

    @GetMapping("/view")
    fun view(
        @RequestParam("substring", defaultValue = "") substring: String,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("pagesize", defaultValue = "1") pagesize: Int
    ): Response<EventViewDto>{
        val viewEvents = eventService.viewEvents(substring, page, pagesize)
        return Response.withData(viewEvents)
    }

    @GetMapping("/choose")
    fun choose(
        @RequestBody eventRequest: EventRequest
    ): Response<EventDto>{
        val chooseEvent = eventService.findEventById(eventRequest)
        return Response.withData(chooseEvent)
    }
}