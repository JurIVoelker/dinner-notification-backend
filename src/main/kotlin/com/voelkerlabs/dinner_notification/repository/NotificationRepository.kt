package com.voelkerlabs.dinner_notification.repository

import com.voelkerlabs.dinner_notification.model.Notification
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationRepository : JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.notificationTo = :notificationTo")
    fun findNotificationsByNotificationTo(
        @Param("notificationTo") notificationTo: Long,
        sort: Sort = Sort.by(Sort.Direction.ASC, "id")
    ): List<Notification>

    fun findByNotificationFrom(
        notificationFrom: Long,
        sort: Sort = Sort.by(Sort.Direction.ASC, "id")
    ): List<Notification>
}