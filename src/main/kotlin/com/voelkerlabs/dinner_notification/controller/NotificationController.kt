package com.voelkerlabs.dinner_notification.controller

import com.voelkerlabs.dinner_notification.dto.NotificationRequestDTO
import com.voelkerlabs.dinner_notification.model.Notification
import com.voelkerlabs.dinner_notification.model.NotificationStatus
import com.voelkerlabs.dinner_notification.repository.NotificationRepository
import com.voelkerlabs.dinner_notification.repository.UserRepository
import com.voelkerlabs.dinner_notification.service.FirebaseService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class NotificationController @Autowired constructor(
    private val firebaseService: FirebaseService,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) {
    @PostMapping("/notifications")
    fun sendNotifications(@RequestBody @Valid body: NotificationRequestDTO): List<Notification> {
        val users = body.userIds.mapNotNull { id -> userRepository.findById(id).orElse(null) }

        val notificationList = mutableListOf<Notification>()

        users.forEach { user ->
            val notification = Notification(
                notificationStatus = NotificationStatus.PENDING,
                notificationFrom = body.notificationFrom,
                notificationTo = user.id
            )
            try {
                firebaseService.sendMessage(
                    user.fcmToken, "Test", "Notification"
                )
            } catch (e: Exception) {
                notification.notificationStatus = NotificationStatus.ERROR
            }
            notificationRepository.save(notification)
            notificationList.add(notification)
        }
        return notificationList
    }

    @GetMapping("/notifications/me")
    fun getOwnNotifications(@RequestParam(value = "id") me: Long): List<Notification> {
        val rawNotifications = notificationRepository.findNotificationsByNotificationTo(me)
        val notificationExpiryMilliseconds: Long = 1000 * 60 * 10
        /* Mark expired notifications */
        val unmarkedExpiredNotifications = rawNotifications.filter { notification ->
            val expirationTime = notification.createdAt?.plusMillis(notificationExpiryMilliseconds)
            (expirationTime?.isBefore(Instant.now())
                ?: false) && notification.notificationStatus != NotificationStatus.EXPIRED
        }
        unmarkedExpiredNotifications.forEach { notification ->
            val updatedNotification = notification
            updatedNotification.notificationStatus = NotificationStatus.EXPIRED
            notificationRepository.save(updatedNotification)
        }
        /* Get and return unexpired notifications */
        val filteredNotifications = rawNotifications.filter { notification ->
            val expirationTime = notification.createdAt?.plusMillis(notificationExpiryMilliseconds)
            expirationTime?.isAfter(Instant.now()) ?: false
        }
        /* Mark pending notifications as received */
        filteredNotifications.forEach { notification ->
            if (notification.notificationStatus === NotificationStatus.PENDING) {
                notification.notificationStatus = NotificationStatus.RECEIVED
                notificationRepository.save(notification)
            }
        }

        return filteredNotifications
    }

    @PutMapping("/notifications/accept")
    fun acceptNotification(@RequestParam(value = "id") id: Long) {
        val notification = notificationRepository.findById(id).orElseThrow()
        notification.notificationStatus = NotificationStatus.ACCEPTED
        notificationRepository.save(notification)
    }

    @PutMapping("/notifications/decline")
    fun declineNotification(@RequestParam(value = "id") id: Long) {
        val notification = notificationRepository.findById(id).orElseThrow()
        notification.notificationStatus = NotificationStatus.DECLINED
        notificationRepository.save(notification)
    }

    @GetMapping("/notifications/trace")
    fun traceNotifications(@RequestParam(value = "id") me: Long): List<Notification> {
        val notificationExpiryMilliseconds: Long = 1000 * 60 * 10
        val rawNotifications = notificationRepository.findByNotificationFrom(me)
        val filteredNotifications = rawNotifications.filter { notification ->
            val expirationTime = notification.createdAt?.plusMillis(notificationExpiryMilliseconds)
            expirationTime?.isAfter(Instant.now()) ?: false
        }

        return filteredNotifications
    }



}