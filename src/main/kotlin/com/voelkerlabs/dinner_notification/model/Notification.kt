package com.voelkerlabs.dinner_notification.model

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
data class Notification (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @CreationTimestamp
    val createdAt: Instant? = null,

    var notificationStatus: NotificationStatus = NotificationStatus.IDLE,

    @Column(name = "notificationFrom")
    val notificationFrom: Long? = null,
    @Column(name = "notificationTo")
    val notificationTo: Long? = null,
)