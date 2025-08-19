package com.voelkerlabs.dinner_notification

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DinnerNotificationApplication

fun main(args: Array<String>) {
	runApplication<DinnerNotificationApplication>(*args)
}
