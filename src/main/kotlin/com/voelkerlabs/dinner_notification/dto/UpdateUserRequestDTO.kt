
package com.voelkerlabs.dinner_notification.dto

data class UpdateUserRequestDTO(
    val fcmToken: String?,
    val userName: String?,
    val id: Long,
    )