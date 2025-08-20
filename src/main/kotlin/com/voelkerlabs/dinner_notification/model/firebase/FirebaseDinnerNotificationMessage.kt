package com.voelkerlabs.dinner_notification.model.firebase

import com.voelkerlabs.dinner_notification.Constants
import java.time.Instant

data class FirebaseDinnerNotificationMessage(
    override val fcmToken: String,
    val createdAt: Instant,
    val notificationFrom: Long,
    val notificationExpiryMilliseconds: Long = Constants.NOTIFICATION_EXPIRY_MILLISECONDS


) : FirebaseMessage {
    fun toJSONString(): String {
        return "{ \"createdAt\": $createdAt, \"notificationFrom\": $notificationFrom, \"notificationExpiryMilliseconds\": $notificationExpiryMilliseconds }"
    }
}