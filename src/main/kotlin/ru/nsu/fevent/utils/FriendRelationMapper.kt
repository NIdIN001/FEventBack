package ru.nsu.fevent.utils

import ru.nsu.fevent.dto.FriendRelationDto
import ru.nsu.fevent.entity.FriendRelation

object FriendRelationMapper {

    fun mapFriendRelationToDto(relation: FriendRelation) = FriendRelationDto(
        status = relation.status,
        from = UserMapper.mapEntityToDto(relation.from),
        to = UserMapper.mapEntityToDto(relation.to),
    )
}