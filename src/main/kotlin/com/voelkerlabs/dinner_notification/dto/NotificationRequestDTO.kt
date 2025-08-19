package com.voelkerlabs.dinner_notification.dto

data class NotificationRequestDTO (
    val userIds: List<Long> = emptyList<Long>(),
    val notificationFrom: Long
)