package ru.nsu.fevent.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.Event

@Repository
interface EventRepository : CrudRepository<Event?, Int?> {
    @Query("select e from Event e where e.name like %?1%")
    fun searchByName(substring: String, pageable: Pageable) : List<Event>

    fun getById(id: Int): Event
}