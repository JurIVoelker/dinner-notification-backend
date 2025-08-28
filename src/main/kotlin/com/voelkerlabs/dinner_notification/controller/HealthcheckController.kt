package com.voelkerlabs.dinner_notification.controller

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthcheckController {

    @GetMapping
    fun health(): ResponseEntity<String> {
        return ResponseEntity(HttpStatusCode.valueOf(200))
    }

}