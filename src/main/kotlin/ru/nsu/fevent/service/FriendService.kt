package ru.nsu.fevent.service

import org.springframework.stereotype.Service
import ru.nsu.fevent.entity.FriendRelation
import ru.nsu.fevent.entity.FriendStatus
import ru.nsu.fevent.entity.User
import ru.nsu.fevent.exception.FriendException
import ru.nsu.fevent.repository.FriendRepository
import ru.nsu.fevent.repository.UserRepository

@Service
class FriendService(
    private val userRepository: UserRepository, private val friendRepository: FriendRepository
) {

    fun acceptFriendRequest(userId: Int, friendId: Int): List<User> {
        val user = getUserById(userId)
        val friend = getUserById(friendId)

        friendRepository.findByUserAndFriendAndStatus(user, friend, FriendStatus.REQUEST)?.let {
            if (it.from == user) {
                return@let
            }

            it.status = FriendStatus.CONFIRMED
            friendRepository.save(it)
        }

        return getFriendsList(user, FriendStatus.CONFIRMED)
    }

    fun addFriend(userId: Int, friendId: Int): List<User> {
        if (userId == friendId) {
            throw FriendException("Пользователь не может добавить в друзья сам себя.")
        }

        val user = getUserById(userId)
        val friend = getUserById(friendId)

        if (friendRepository.containsRelation(user, friend)) {
            throw FriendException("Заявка в друзья уже существует.")
        }

        val relation = FriendRelation(
            from = user, to = friend, status = FriendStatus.REQUEST
        )

        friendRepository.save(relation)

        return getFriendsList(user, FriendStatus.REQUEST)
    }

    fun deleteFriend(userId: Int, friendId: Int, status: FriendStatus): List<User> {
        val user = getUserById(userId)
        val friend = getUserById(friendId)

        friendRepository.findByUserAndFriendAndStatus(user, friend, status)?.let {
            friendRepository.delete(it)
        }

        return getFriendsList(user, status)
    }

    fun getFriendsList(userId: Int, status: FriendStatus): List<FriendRelation> {
        val user = getUserById(userId)

        return friendRepository.findAllByUserAndStatus(user, status)
    }

    fun getFriendsCount(userId: Int, status: FriendStatus): Int {
        val user = getUserById(userId)

        return friendRepository.findCountByUserAndStatus(user, status)
    }

    fun getFriendsList(user: User, status: FriendStatus): List<User> {
        return friendRepository.findAllByUserAndStatus(user, status)
            .map { relation -> getFriendFromRelation(relation, user) }
            .toList()
    }

    private fun getFriendFromRelation(relation: FriendRelation, user: User): User {
        return if (relation.from == user) relation.to
        else relation.from
    }

    private fun getUserById(userId: Int): User {
        return userRepository.findById(userId)
            .orElseThrow { FriendException("Пользователь с id $userId не найден") }
            ?: throw FriendException("Пользователь с id $userId не найден")
    }
}
