package com.voelkerlabs.dinner_notification.controller

import com.voelkerlabs.dinner_notification.dto.UpdateUserRequestDTO
import com.voelkerlabs.dinner_notification.dto.UserDTO
import com.voelkerlabs.dinner_notification.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(
    private val userService: UserService
) {
    @GetMapping("/users")
    fun getUsers(): List<UserDTO> {
        val users = userService.findAll()
        return userService.toDTO(users)
    }

    @PutMapping("/user")
    fun updateUser(@RequestBody @Valid body: UpdateUserRequestDTO): UserDTO {
        val existingUser = userService.findById(body.id)
        if (body.fcmToken != null) existingUser.fcmToken = body.fcmToken
        if (body.userName != null) existingUser.userName = body.userName
        userService.save(existingUser)
        return userService.toDTO(existingUser)
    }

    @DeleteMapping("/user/{id}")
    fun deleteUser(@PathVariable("id") id: Long) {
        userService.deleteUser(id)
    }
}