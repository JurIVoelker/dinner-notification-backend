package com.voelkerlabs.dinner_notification.service

import com.voelkerlabs.dinner_notification.Constants
import com.voelkerlabs.dinner_notification.dto.NotificationRatingResponseDTO
import com.voelkerlabs.dinner_notification.model.NotificationStatus
import com.voelkerlabs.dinner_notification.repository.NotificationRepository
import com.voelkerlabs.dinner_notification.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NotificationService @Autowired constructor(
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository
) {
    fun getPointsToAdd(notificationCreation: Instant): Int {
        val timeToReact = System.currentTimeMillis() - notificationCreation.toEpochMilli()
        val pointsToAdd =
            ((Constants.NOTIFICATION_EXPIRY_MILLISECONDS - timeToReact).toFloat() / Constants.NOTIFICATION_EXPIRY_MILLISECONDS) * Constants.MAX_POINTS
        return pointsToAdd.toInt().coerceAtLeast(0).coerceAtMost(Constants.MAX_POINTS)
    }

    fun handleNotificationRating(notificationId: Long, notificationStatus: NotificationStatus): NotificationRatingResponseDTO {
        val notification = notificationRepository.findById(notificationId).orElseThrow()
        val user = userRepository.findById(notification.notificationTo ?: throw NullPointerException()).orElseThrow()
        var points = user.points ?: 0
        var pointsToAdd = 0
        if (notification.isNotRated() && notification.createdAt != null) {
            pointsToAdd = getPointsToAdd(notification.createdAt)
            points += pointsToAdd
            user.points = points
            userRepository.save(user)
        }
        notification.notificationStatus = notificationStatus
        notificationRepository.save(notification)
        return NotificationRatingResponseDTO(pointsToAdd = pointsToAdd)
    }
}