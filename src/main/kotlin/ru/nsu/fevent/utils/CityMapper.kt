package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.CitiesResponse
import ru.nsu.fevent.entity.City

object CityMapper {
    fun mapListOfCitiesToCitiesResponse(cities: List<City>): CitiesResponse {
        val citiesNames = cities.stream()
            .map { city -> city.name }
            .toList()
        return CitiesResponse(citiesNames)
    }
}