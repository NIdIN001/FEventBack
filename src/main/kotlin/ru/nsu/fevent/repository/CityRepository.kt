package ru.nsu.fevent.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.City

@Repository
interface CityRepository : CrudRepository<City, Int> {
    fun findByNameStartsWithIgnoreCase(name: String): List<City>
}