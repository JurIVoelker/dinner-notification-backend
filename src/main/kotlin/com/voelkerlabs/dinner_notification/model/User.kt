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

    @Column(name = "fcmToken", updatable = true, nullable = true)
    var fcmToken: String? = null,

    @UpdateTimestamp
    @Column(name = "updatedAt")
    val updatedAt: Instant? = null,

    @Column(name = "userName")
    val userName: String = "",

    @Column(name = "points")
    var points: Long? = 0,
)