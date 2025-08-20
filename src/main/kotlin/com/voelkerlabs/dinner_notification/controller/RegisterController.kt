package com.voelkerlabs.dinner_notification.controller

import com.voelkerlabs.dinner_notification.dto.RegisterRequestDTO
import com.voelkerlabs.dinner_notification.dto.UserDTO
import com.voelkerlabs.dinner_notification.model.User
import com.voelkerlabs.dinner_notification.repository.UserRepository
import com.voelkerlabs.dinner_notification.service.UserService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register")
class RegisterController @Autowired constructor(private val userRepository: UserRepository, private val userService: UserService) {
    @PostMapping
    fun register(@RequestBody @Valid body: RegisterRequestDTO): UserDTO {
        val user = User(fcmToken = body.fcmToken, userName = body.userName)
        userService.save(user)
        return userService.toDTO(user)
    }
}
