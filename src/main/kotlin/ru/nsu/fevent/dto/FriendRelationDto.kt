package ru.nsu.fevent.dto

import ru.nsu.fevent.entity.FriendStatus

data class FriendRelationDto(
    val status: FriendStatus,

    val from: UserDto,

    val to: UserDto
)