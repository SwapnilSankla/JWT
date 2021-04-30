package com.swapnilsankla.jwt.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
	@Autowired val encryptionFactory: EncryptionFactory,
	@Value("\${auth-service.token.expiryInSeconds}") val tokenExpiryInSeconds: Int
) {
	fun generate(customerId: String): String {
		return Jwts
			.builder()
			.claim("customerId", customerId)
			.setExpiration(setExpiry())
			.signWith(encryptionFactory.get().key())
			.compact()
	}

	fun validate(token: String): Boolean {
		return try {
			parseToken(token)
			true
		} catch (exception: Exception) {
			false
		}
	}

	fun claimsFromToken(token: String): Map<String, Any> {
		return (parseToken(token)?.body as? Claims)?.toMap() ?: emptyMap()
	}

	private fun setExpiry() = Date.from(Date().toInstant().plusSeconds(tokenExpiryInSeconds.toLong()))

	private fun parseToken(token: String): Jwt<out Header<*>, *>? {
		return Jwts
			.parserBuilder()
			.setSigningKey(encryptionFactory.get().key())
			.build()
			.parse(token)
	}
}
