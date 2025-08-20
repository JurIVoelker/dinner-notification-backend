package com.voelkerlabs.dinner_notification.exception

import java.lang.RuntimeException

class UserNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
}