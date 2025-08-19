package com.voelkerlabs.dinner_notification

import com.voelkerlabs.dinner_notification.service.NotificationService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import kotlin.test.assertEquals

@SpringBootTest
class NotificationServiceTest {


	@Autowired
	private lateinit var notificationService: NotificationService

	@Test
	fun `test getPointsToAdd when notification is created half of expiry time minutes ago should result in 50`() {
		val timeInPast = Instant.now().minusMillis(Constants.NOTIFICATION_EXPIRY_MILLISECONDS / 2)

		val expectedPoints = Constants.MAX_POINTS / 2

		val resultPoints = notificationService.getPointsToAdd(timeInPast)
		// Assert: The returned points should be 1/2 MAX_POINTS
		assertEquals(expectedPoints, resultPoints)
	}

	@Test
	fun `test getPointsToAdd with negative result should result in 0`() {
		val timeInPast = Instant.now().minusMillis(Constants.NOTIFICATION_EXPIRY_MILLISECONDS * 2)
		val expectedPoints = 0
		val resultPoints = notificationService.getPointsToAdd(timeInPast)
		assertEquals(expectedPoints, resultPoints)
	}

	@Test
	fun `test getPointsToAdd with future result should result in MAX_POINTS`() {
		val timeInFuture = Instant.now().plusMillis(Constants.NOTIFICATION_EXPIRY_MILLISECONDS * 2)
		val expectedPoints = Constants.MAX_POINTS
		val resultPoints = notificationService.getPointsToAdd(timeInFuture)
		assertEquals(expectedPoints, resultPoints)
	}
}
