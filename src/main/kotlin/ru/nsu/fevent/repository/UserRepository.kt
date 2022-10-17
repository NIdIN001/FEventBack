package ru.nsu.fevent.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.nsu.fevent.entity.User

@Repository
interface UserRepository : CrudRepository<User?, Int?> {

    fun findFirstByLogin(login: String): User?

    fun findFirstByPhoneNumber(phoneNumber: String): User?
}