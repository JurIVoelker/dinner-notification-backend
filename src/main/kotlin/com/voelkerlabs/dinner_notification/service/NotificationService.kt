package com.voelkerlabs.dinner_notification.service

import com.voelkerlabs.dinner_notification.Constants
import com.voelkerlabs.dinner_notification.dto.NotificationRatingResponseDTO
import com.voelkerlabs.dinner_notification.model.Notification
import com.voelkerlabs.dinner_notification.model.NotificationStatus
import com.voelkerlabs.dinner_notification.repository.NotificationRepository
import com.voelkerlabs.dinner_notification.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
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

    fun handleNotificationRating(
        notificationId: Long,
        notificationStatus: NotificationStatus
    ): NotificationRatingResponseDTO {
        val notification = notificationRepository.findById(notificationId).orElseThrow()
        val user = userRepository.findById(notification.notificationTo).orElseThrow()
        var points = user.points
        var pointsToAdd = 0
        if (notification.isNotRated() && notification.createdAt != null) {
            pointsToAdd = getPointsToAdd(notification.createdAt)
            points += pointsToAdd
            user.points = points
            userRepository.save(user)
        }
        notification.notificationStatus = notificationStatus
        notificationRepository.save(notification)
        return NotificationRatingResponseDTO(pointsToAdd = pointsToAdd, totalPoints = points.toInt())
    }

    fun save(notification: Notification) {
        notificationRepository.save(notification)
    }

    fun findNotificationsByNotificationTo(notificationTo: Long): List<Notification> {
        return notificationRepository.findNotificationsByNotificationTo(notificationTo)
    }

    fun getUnexpiredNotifications(notifications: List<Notification>): List<Notification> {
        return notifications.filter { notification ->
            !notification.isExpired()
        }
    }

    fun markExpiredNotifications(notifications: List<Notification>) {
        val expiredNotifications = notifications.filter { notification ->
            notification.isExpired()
        }
        expiredNotifications.forEach { notification ->
            notification.notificationStatus = NotificationStatus.EXPIRED
            save(notification)
        }
    }

    fun markPendingNotificationsAsReceived(notifications: List<Notification>): List<Notification> {
        val unexpiredNotifications = getUnexpiredNotifications(notifications)
        unexpiredNotifications.forEach { notification ->
            if (notification.notificationStatus === NotificationStatus.PENDING) {
                notification.notificationStatus = NotificationStatus.RECEIVED
                save(notification)
            }
        }
        return unexpiredNotifications
    }

    fun findByNotificationsFrom(userId: Long): List<Notification> {
        return notificationRepository.findByNotificationFrom(userId)
    }


}