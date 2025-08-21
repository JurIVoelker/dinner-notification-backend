package com.voelkerlabs.dinner_notification.repository

import com.voelkerlabs.dinner_notification.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findFirstByUserName(userName: String): User?
}