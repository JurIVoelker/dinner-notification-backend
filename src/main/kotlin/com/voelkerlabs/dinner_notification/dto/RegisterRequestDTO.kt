package com.voelkerlabs.dinner_notification.dto

import jakarta.validation.constraints.NotBlank

data class RegisterRequestDTO (
    @field:NotBlank(message = "fcmToken must not be empty")
    val fcmToken: String,

    @field:NotBlank(message = "userName must not be empty")
    val userName: String,
)