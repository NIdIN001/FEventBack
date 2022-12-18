package ru.nsu.fevent.controller

import org.springframework.web.bind.annotation.*
import ru.nsu.fevent.dto.FriendRelationDto
import ru.nsu.fevent.dto.Response
import ru.nsu.fevent.dto.UserDto
import ru.nsu.fevent.dto.FriendRequest
import ru.nsu.fevent.entity.FriendStatus
import ru.nsu.fevent.service.FriendService
import ru.nsu.fevent.utils.FriendRelationMapper
import ru.nsu.fevent.utils.JwtUtils
import ru.nsu.fevent.utils.UserMapper

@RestController
@RequestMapping("/friends")
class FriendController(
    private val friendService: FriendService
) {

    @GetMapping("/{status}")
    fun getFriendsList(
        @CookieValue(name = "accessToken") accessToken: String,
        @PathVariable status: FriendStatus
    ): Response<List<FriendRelationDto>> {
        val userId = JwtUtils.getUserIdByAccessToken(accessToken)
        val result = friendService.getFriendsList(userId, status)

        return Response.withData(result.stream()
            .map { relation -> FriendRelationMapper.mapFriendRelationToDto(relation) }
            .toList())
    }

    @GetMapping("/count/{status}")
    fun getFriendsCount(
        @CookieValue(name = "accessToken") accessToken: String,
        @PathVariable status: FriendStatus
    ): Response<Int> {
        val userId = JwtUtils.getUserIdByAccessToken(accessToken)
        return Response.withData(friendService.getFriendsCount(userId, status))
    }

    @PostMapping("/add")
    fun addFriend(
        @CookieValue(name = "accessToken") accessToken: String,
        @RequestBody friendRequest: FriendRequest
    ): Response<List<UserDto>> {
        val userId = JwtUtils.getUserIdByAccessToken(accessToken)
        val result = friendService.addFriend(userId, friendRequest.friendId)

        return Response.withData(result.stream()
            .map { user -> UserMapper.mapEntityToDto(user) }
            .toList()
        )
    }

    @PostMapping("/accept")
    fun acceptFriend(
        @CookieValue(name = "accessToken") accessToken: String,
        @RequestBody acceptFriendRequest: FriendRequest
    ): Response<List<UserDto>> {
        val userId = JwtUtils.getUserIdByAccessToken(accessToken)
        val result = friendService.acceptFriendRequest(userId, acceptFriendRequest.friendId)

        return Response.withData(result.stream()
            .map { user -> UserMapper.mapEntityToDto(user) }
            .toList()
        )
    }

    @PostMapping("/delete/{status}")
    fun deleteRequestFriend(
        @CookieValue(name = "accessToken") accessToken: String,
        @RequestBody deleteFriendRequest: FriendRequest,
        @PathVariable status: FriendStatus
    ): Response<List<UserDto>> {
        val userId = JwtUtils.getUserIdByAccessToken(accessToken)
        val result = friendService.deleteFriend(userId, deleteFriendRequest.friendId, status)

        return Response.withData(result.stream()
            .map { user -> UserMapper.mapEntityToDto(user) }
            .toList()
        )
    }
}
