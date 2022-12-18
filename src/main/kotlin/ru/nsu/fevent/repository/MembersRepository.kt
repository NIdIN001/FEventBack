package ru.nsu.fevent.repository

import org.springframework.data.repository.CrudRepository
import ru.nsu.fevent.entity.Members

interface MembersRepository : CrudRepository<Members, Int> {
}