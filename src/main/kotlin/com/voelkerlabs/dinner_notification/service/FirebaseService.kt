package com.voelkerlabs.dinner_notification.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.voelkerlabs.dinner_notification.model.firebase.FirebaseDinnerNotificationMessage
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class FirebaseService {

    @PostConstruct
    fun initialize() {
        initializeFirebaseClient()
    }

    fun initializeFirebaseClient() {
        val resourceStream: InputStream? = this::class.java.getResourceAsStream("/firebase-cert.json")
            ?: throw IllegalArgumentException("Firebase service account file not found")

        val options = FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(resourceStream)).build()
        FirebaseApp.initializeApp(options)
    }

    fun sendMessage(notification: FirebaseDinnerNotificationMessage) {
        val message =
            Message.builder().setToken(notification.fcmToken).putData("createdAt", notification.createdAt.toString())
                .build()

        val response = FirebaseMessaging.getInstance().send(message)
        println("Sent message with response: $response")
    }
}