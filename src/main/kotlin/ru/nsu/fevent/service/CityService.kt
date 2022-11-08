package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.dto.CityDto
import ru.nsu.fevent.repository.CityRepository
import ru.nsu.fevent.utils.CityMapper

@Service
class CityService (val cityRepository: CityRepository) {
    fun getCitiesStartsWith(name: String): List<CityDto> {
        val cities =  cityRepository.findByNameStartsWithIgnoreCase(name)
        return cities.stream()
            .map { city -> CityMapper.mapCityToDto(city) }
            .toList()
    }
}