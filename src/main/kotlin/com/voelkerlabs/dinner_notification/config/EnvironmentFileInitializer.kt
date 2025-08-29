package com.voelkerlabs.dinner_notification.config

import com.voelkerlabs.dinner_notification.exception.FirebaseSetupError
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

@Component
class EnvironmentFileInitializer {
    @PostConstruct
    fun init() {
        val env = System.getenv("FIREBASE_CERT")
        val filePath = "src/main/resources/firebase-cert.json"

        if (!File(filePath).exists()) {
            if (env != null) {
                File(filePath).writeText(env)
                println("Firebase cert created at $filePath")
            } else {
                throw FirebaseSetupError()
            }
        }
    }
}
