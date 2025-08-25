package com.voelkerlabs.dinner_notification.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "fcm_token", updatable = true, nullable = false)
    var fcmToken: String = "",

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,

    @Column(name = "user_name", nullable = false, unique = true)
    var userName: String = "",

    @Column(name = "points", nullable = false)
    var points: Long = 0,
)