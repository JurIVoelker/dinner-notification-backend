package com.voelkerlabs.dinner_notification.service

import com.voelkerlabs.dinner_notification.dto.UserDTO
import com.voelkerlabs.dinner_notification.exception.UserNotFoundException
import com.voelkerlabs.dinner_notification.model.User
import com.voelkerlabs.dinner_notification.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException("User not found by id $id") }
    }

    fun findByUserName(userName: String): User? {
        return userRepository.findFirstByUserName(userName = userName)
    }

    fun save(user: User) {
        userRepository.save(user)
    }

    fun toDTO(user: User): UserDTO {
        return UserDTO(user.id, user.userName, user.points)
    }

    fun toDTO(users: List<User>): List<UserDTO> {
        return users.map { user -> UserDTO(user.id, user.userName, points = user.points ?: 0) }
    }

    fun findByIdsNotNull(userIds: List<Long>): List<User> {
        return userIds.mapNotNull { id -> userRepository.findById(id).orElse(null) }
    }

    fun deleteUser(id: Long) {
        return userRepository.deleteById(id)
    }
}
