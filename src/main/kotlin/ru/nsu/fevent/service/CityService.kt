package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.CitiesResponse
import ru.nsu.fevent.repository.CityRepository
import ru.nsu.fevent.utils.CityMapper

@Service
class CityService (val cityRepository: CityRepository) {
    fun getCitiesStartsWith(name: String): CitiesResponse {
        val cities =  cityRepository.findByNameStartsWithIgnoreCase(name)
        return CityMapper.mapListOfCitiesToCitiesResponse(cities)
    }
}