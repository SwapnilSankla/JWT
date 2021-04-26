package com.swapnilsankla.jwt.auth.service

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

	fun generate(): String {
		return Jwts
			.builder()
			.setExpiration(setExpiry())
			.signWith(generatePrivateKeyFromSecret())
			.compact()
	}

	private fun setExpiry() = Date.from(Date().toInstant().plusSeconds(tokenExpiryInSeconds.toLong()))

	fun validate(token: String): Boolean {
		return try {
			Jwts
				.parserBuilder()
				.setSigningKey(generatePrivateKeyFromSecret())
				.build()
				.parse(token)
			true
		} catch (exception: Exception) {
			false
		}
	}

	private fun generatePrivateKeyFromSecret() = Keys.hmacShaKeyFor(secret.toByteArray())
}
