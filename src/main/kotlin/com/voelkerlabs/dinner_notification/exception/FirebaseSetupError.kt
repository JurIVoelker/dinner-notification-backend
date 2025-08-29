package com.voelkerlabs.dinner_notification.exception

import java.lang.RuntimeException

class FirebaseSetupError: RuntimeException {
    constructor() : super("Error while setting up Firebase Messaging. Neither the firebase file does exist, nor the environment variable FIREBASE_CERT is set")
}

