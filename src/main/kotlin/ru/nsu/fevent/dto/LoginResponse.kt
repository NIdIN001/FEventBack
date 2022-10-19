package ru.nsu.fevent.dto

import ru.nsu.fevent.utils.JwtPair

data class LoginResponse(
    val jwtPair: JwtPair,
    val user: UserDto
)
