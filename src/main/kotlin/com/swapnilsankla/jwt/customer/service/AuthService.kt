package com.swapnilsankla.jwt.customer.service

import com.swapnilsankla.jwt.authvalidator.TokenResolver
import com.swapnilsankla.jwt.microtype.TokenSigningMethod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AuthService(
	@Autowired private val restTemplate: RestTemplate,
	@Autowired private val tokenResolver: TokenResolver,
	@Value("\${auth-service.validateToken.endpoint}") val authEndpoint: String,
	@Value("\${token.signing.method}") val tokenSigningMethod: String
) {
	fun isTokenValid(token: String): Boolean {
		return if (tokenSigningMethod == TokenSigningMethod.Symmetric.name)
			restTemplate.getForEntity(authEndpoint.replace("{token}", token), Boolean::class.java).body ?: false
		else tokenResolver.validateTokenWithPublicKey(token)
	}
}
