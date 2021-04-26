package com.swapnilsankla.jwt.customer.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthService(
	@Autowired private val restTemplate: RestTemplate,
	@Value("\${auth-service.validateToken.endpoint}") val authEndpoint: String
) {
	fun isTokenValid(token: String) =
		restTemplate.getForEntity(authEndpoint.replace("{token}", token), Boolean::class.java).body ?: false
}