package ru.nsu.fevent.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.Event

@Repository
interface EventRepository : CrudRepository<Event?, Int?> {

    fun findAllByName(name: String, pageable: Pageable) : List<Event>

    fun getById(id: Int): Event
}