package ru.nsu.fevent.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.Event

@Repository
interface EventRepository : CrudRepository<Event?, Int?> {

    fun findAll(pageable: Pageable) : List<Event>

    fun getById(id: Int): Event

    @Query(
        value = "SELECT e " +
                "FROM Event e " +
                "ORDER BY (ru.nsu.fevent.utils.CoordinatesUtils.EARTH_RADIUS_IN_METRES * " +
                    "acos(cos(radians(:latitude)) * cos(radians(e.latitude)) * " +
                    "cos(radians(e.longitude) - radians(:longitude)) + " +
                    "sin(radians(:latitude)) * sin(radians(e.latitude))))",
        countQuery = "SELECT count(e) FROM Event e"
    )
    fun findClosest(@Param("latitude") latitude: Double,
                    @Param("longitude") longitude: Double,
                    pageable: Pageable): Page<Event>

}