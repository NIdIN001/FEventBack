package ru.nsu.fevent.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.Event

@Repository
interface EventRepository : CrudRepository<Event?, Int?> {
}