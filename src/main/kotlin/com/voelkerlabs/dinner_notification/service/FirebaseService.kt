package com.voelkerlabs.dinner_notification.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.voelkerlabs.dinner_notification.exception.FirebaseSetupError
import com.voelkerlabs.dinner_notification.model.firebase.FirebaseDinnerNotificationMessage
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

@Service
class FirebaseService {

    @PostConstruct
    fun initialize() {
        initializeFirebaseClient()
    }

    fun initializeFirebaseClient() {
        val filePath = "src/main/resources/firebase-cert.json"
        var resourceStream: InputStream?;

        if (!File(filePath).exists()) {
            val env = System.getenv("FIREBASE_CERT")
            println("Using firebase cert from env")
            if (env == null) {
                throw FirebaseSetupError()
            }
            resourceStream = ByteArrayInputStream(env.toByteArray(Charsets.UTF_8))
        } else {
            println("Using firebase cert from file")
            resourceStream = this::class.java.getResourceAsStream("/firebase-cert.json")
                ?: throw IllegalArgumentException("Firebase service account file not found")
        }


        val options = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resourceStream)).build()
        FirebaseApp.initializeApp(options)
    }

    fun sendMessage(notification: FirebaseDinnerNotificationMessage) {
        val message =
            Message.builder().setToken(notification.fcmToken).putData("createdAt", notification.createdAt.toString())
                .setNotification(
                    Notification.builder().setTitle("Essen!")
                        .setBody("Essen ist fertig. Klicke auf die Benachrichtigung und bewege dich Richtung Essen.")
                        .build()
                ).build()

        val response = FirebaseMessaging.getInstance().send(message)
        println("Sent message with response: $response")
    }
}
