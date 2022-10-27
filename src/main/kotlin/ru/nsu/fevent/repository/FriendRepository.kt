package ru.nsu.fevent.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.nsu.fevent.entity.FriendRelation
import ru.nsu.fevent.entity.FriendStatus
import ru.nsu.fevent.entity.User

interface FriendRepository : CrudRepository<FriendRelation, Int> {
    @Query(
        value = "SELECT relation FROM FriendRelation relation " +
                "WHERE " +
                "(relation.from = :user OR relation.to = :user) AND " +
                "relation.status = :status"
    )
    fun findAllByUserAndStatus(
        @Param("user") user: User,
        @Param("status") status: FriendStatus
    ): List<FriendRelation>

    @Query(
        value = "SELECT relation FROM FriendRelation relation " +
                "WHERE " +
                "(relation.from = :user AND relation.to = :friend) OR " +
                "(relation.to = :user AND relation.from = :friend) AND " +
                "relation.status = :status"
    )
    fun findByUserAndFriendAndStatus(
        @Param("user") user: User,
        @Param("friend") friend: User,
        @Param("status") status: FriendStatus
    ): FriendRelation?

    @Query(
        value = "SELECT COUNT (relation) FROM FriendRelation relation " +
                "WHERE " +
                "(relation.from = :user OR relation.to = :user) AND " +
                "relation.status = :status"
    )
    fun findCountByUserAndStatus(
        @Param("user") user: User,
        @Param("status") status: FriendStatus
    ): Int

    @Query(
        value = "SELECT (COUNT (relation) > 0) FROM FriendRelation relation " +
                "WHERE " +
                "(relation.from = :user AND relation.to = :friend) OR " +
                "(relation.to = :user AND relation.from = :friend)"
    )
    fun containsRelation(
        @Param("user") user: User,
        @Param("friend") friend: User
    ): Boolean
}
