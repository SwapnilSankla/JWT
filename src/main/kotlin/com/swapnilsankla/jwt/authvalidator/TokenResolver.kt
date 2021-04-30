package com.swapnilsankla.jwt.authvalidator

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TokenResolver(@Autowired private val signingKeyResolver: SigningKeyResolver) {
	fun getClaims(token: String): Map<String, Any> {
		return (parseToken(token)?.body as? Claims)?.toMap() ?: emptyMap()
	}

	fun validateTokenWithPublicKey(token: String): Boolean {
		return try {
			parseToken(token)
			true
		} catch (exception: Exception) {
			false
		}
	}

	private fun parseToken(token: String): Jwt<out Header<*>, *>? {
		return Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parse(token)
	}
}