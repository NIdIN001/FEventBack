package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.CitiesRequest
import ru.nsu.fevent.dto.CityDto
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.service.CityService

@RestController
@RequestMapping("/city")
class CityController (private val cityService: CityService) {
    @PostMapping
    fun getCitiesStartsWith(@RequestBody citiesRequest: CitiesRequest): Response<List<CityDto>> {
        val cities = cityService.getCitiesStartsWith(citiesRequest.cityName)
        return Response.withData(cities)
    }
}