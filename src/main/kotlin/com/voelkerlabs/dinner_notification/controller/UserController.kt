package com.voelkerlabs.dinner_notification.controller

import com.voelkerlabs.dinner_notification.dto.UpdateUserRequestDTO
import com.voelkerlabs.dinner_notification.dto.UserDTO
import com.voelkerlabs.dinner_notification.model.User
import com.voelkerlabs.dinner_notification.repository.UserRepository
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(private val userRepository: UserRepository) {
    @GetMapping("/users")
    fun getUsers(): List<UserDTO> {
        val users = userRepository.findAll()
        return users.map{user -> UserDTO(user.id, user.userName) }
    }

    @PutMapping("/user")
    fun updateUser(@RequestBody @Valid body: UpdateUserRequestDTO): String {
        val existingUser = userRepository.findById(body.id).orElseThrow()
        existingUser.fcmToken = body.fcmToken
        userRepository.save(existingUser)
        return "OK"
    }
}