package com.swapnilsankla.jwt.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
	@Value("\${auth-service.secret}") val secret: String,
	@Value("\${auth-service.token.expiryInSeconds}") val tokenExpiryInSeconds: Int
) {

	fun generate(customerId: String): String {
		return Jwts
			.builder()
			.claim("customerId", customerId)
			.setExpiration(setExpiry())
			.signWith(generatePrivateKeyFromSecret())
			.compact()
	}

	private fun setExpiry() = Date.from(Date().toInstant().plusSeconds(tokenExpiryInSeconds.toLong()))

	fun validate(token: String): Boolean {
		return try {
			parseToken(token)
			true
		} catch (exception: Exception) {
			false
		}
	}

	private fun parseToken(token: String): Jwt<out Header<*>, *>? {
		return Jwts
			.parserBuilder()
			.setSigningKey(generatePrivateKeyFromSecret())
			.build()
			.parse(token)
	}

	fun claimsFromToken(token: String): Map<String, Any> {
		return (parseToken(token)?.body as? Claims)?.toMap() ?: emptyMap()
	}

	private fun generatePrivateKeyFromSecret() = Keys.hmacShaKeyFor(secret.toByteArray())
}
