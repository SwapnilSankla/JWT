package com.swapnilsankla.jwt.ui.service

import com.swapnilsankla.jwt.authvalidator.TokenResolver
import com.swapnilsankla.jwt.microtype.TokenSigningMethod
import com.swapnilsankla.jwt.ui.model.FailedToGenerateTokenException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthServiceInUIPackage(
	@Autowired private val restTemplate: RestTemplate,
	@Autowired private val tokenResolver: TokenResolver,
	@Value("\${auth-service.generateToken.endpoint}") val authGenerateTokenEndpoint: String,
	@Value("\${auth-service.claims.endpoint}") val authTokenClaimsEndpoint: String,
	@Value("\${token.signing.method}") val tokenSigningMethod: String
) {
	fun generateToken(customerId: String): String {
		val generateTokenRequest = GenerateTokenRequest(customerId)
		return restTemplate
			.postForEntity(authGenerateTokenEndpoint, generateTokenRequest, String::class.java).body
			?: throw FailedToGenerateTokenException("Token cannot be generated right now")
	}

	fun isUserAuthorised(token: String, customerId: String): Boolean {
		val customerIdInToken =
			if (tokenSigningMethod == TokenSigningMethod.Symmetric.name) getCustomerIdFromAuthService(token)
			else getCustomerIdFromToken(token)
		return customerIdInToken?.equals(customerId) == true
	}

	private fun getCustomerIdFromAuthService(token: String): String? {
		val claims = restTemplate.getForEntity(
			authTokenClaimsEndpoint.replace("{token}", token),
			Map::class.java
		).body as? Map<String, String>
		return claims?.get("customerId")
	}

	private fun getCustomerIdFromToken(token: String): String? {
		return tokenResolver.getClaims(token)["customerId"] as? String
	}
}

data class GenerateTokenRequest(val customerId: String)