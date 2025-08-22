package com.voelkerlabs.dinner_notification.model

import com.voelkerlabs.dinner_notification.Constants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(name = "notification")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant? = null,

    var notificationStatus: NotificationStatus = NotificationStatus.IDLE,

    @Column(name = "notification_from", nullable = false)
    val notificationFrom: Long = -1,
    @Column(name = "notification_to", nullable = false)
    val notificationTo: Long = -1,
) {
    fun isNotRated(): Boolean {
        return this.notificationStatus == NotificationStatus.PENDING || this.notificationStatus == NotificationStatus.RECEIVED || this.notificationStatus == NotificationStatus.IDLE
    }

    fun expirationTime(): Instant {
        val expirationTime = createdAt?.plusMillis(Constants.NOTIFICATION_EXPIRY_MILLISECONDS)
        return expirationTime ?: Instant.now()
    }

    fun isExpired(): Boolean {
        return (expirationTime().isBefore(Instant.now())) || notificationStatus == NotificationStatus.EXPIRED
    }
}
