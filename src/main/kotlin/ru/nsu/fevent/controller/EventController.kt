package ru.nsu.fevent.controller

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.*
import ru.nsu.fevent.service.EventService
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.Location
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
        @RequestParam("name", defaultValue = "") name: String,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("pagesize", defaultValue = "10") pagesize: Int
    ): Response<EventViewDto>{
        val viewEvents = eventService.viewEvents(name, page, pagesize)
        return Response.withData(viewEvents)
    }

    @GetMapping("/choose/{id}")
    fun choose(
        @PathVariable id: Int
    ): Response<EventDto>{
        val chooseEvent = eventService.findEventById(id)
        return Response.withData(chooseEvent)
    }

    @GetMapping("/closest")
    fun getAllEventsSortedByDistance(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        pageable: Pageable
        ): Response<Page<EventWithDistanceDto>> {
            val userLocation = Location(latitude, longitude)
            return Response.withData(eventService.findAllWithDistance(userLocation, pageable))
    }
}