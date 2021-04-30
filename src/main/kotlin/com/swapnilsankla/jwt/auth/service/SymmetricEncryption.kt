package com.swapnilsankla.jwt.auth.service

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.security.Key
import java.security.KeyStore
import java.security.PrivateKey

@Component
class SymmetricEncryption(@Value("\${auth-service.secret}") val secret: String) : Encryption {
	override fun key(): Key {
		return Keys.hmacShaKeyFor(secret.toByteArray()) ?: throw java.lang.Exception()
	}
}