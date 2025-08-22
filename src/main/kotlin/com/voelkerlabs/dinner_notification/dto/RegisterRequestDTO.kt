package com.voelkerlabs.dinner_notification.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequestDTO (
    @field:NotBlank(message = "fcmToken must not be empty")
    val fcmToken: String,

    @field:NotBlank(message = "userName must not be empty")
    @field:Size(min = 3)
    val userName: String,
)