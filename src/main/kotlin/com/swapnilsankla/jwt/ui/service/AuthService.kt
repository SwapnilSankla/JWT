package com.swapnilsankla.jwt.ui.service

import com.swapnilsankla.jwt.ui.model.FailedToGenerateTokenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthServiceInUIPackage(
	@Autowired private val restTemplate: RestTemplate,
	@Value("\${auth-service.generateToken.endpoint}") val authGenerateTokenEndpoint: String,
	@Value("\${auth-service.claims.endpoint}") val authTokenClaimsEndpoint: String
) {
	fun generateToken(customerId: String): String {
		val generateTokenRequest = GenerateTokenRequest(customerId)
		return restTemplate
			.postForEntity(authGenerateTokenEndpoint, generateTokenRequest, String::class.java).body
			?: throw FailedToGenerateTokenException("Token cannot be generated right now")
	}

	fun isUserAuthorised(token: String, customerId: String): Boolean {
		return try {
			val claims = restTemplate.getForEntity(
				authTokenClaimsEndpoint.replace("{token}", token),
				Map::class.java
			).body as? Map<String, String>
			val customerIdInToken = claims?.get("customerId") ?: return false
			customerIdInToken == customerId
		} catch (exception: Exception) {
			false
		}
	}
}

data class GenerateTokenRequest(val customerId: String)