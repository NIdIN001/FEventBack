package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.CityDto
import ru.nsu.fevent.entity.City

object CityMapper {
    fun mapCityToDto(city: City): CityDto =
        CityDto(
            city.name,
            city.region.name,
            city.region.district.name
        )
}