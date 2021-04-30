package com.swapnilsankla.jwt.auth.service

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class SymmetricEncryption(@Value("\${auth-service.secret}") val secret: String) : Encryption {
	override fun signingKey(): Key {
		return Keys.hmacShaKeyFor(secret.toByteArray()) ?: throw java.lang.Exception()
	}

	override fun encrypt(claim: String): String {
		val cipher = cipherInstance(Cipher.ENCRYPT_MODE)
		val encrypted = cipher.doFinal(claim.toByteArray())
		return Base64.getEncoder().encodeToString(encrypted)
	}

	override fun decrypt(encryptedClaim: String): String {
		val cipher = cipherInstance(Cipher.DECRYPT_MODE)
		val original = cipher.doFinal(Base64.getDecoder().decode(encryptedClaim))
		return String(original)
	}

	private fun cipherInstance(cipherMode: Int): Cipher {
		val ivParameterSpec = IvParameterSpec(secret.substring(0, 16).toByteArray())
		val secretKeySpec = SecretKeySpec(secret.substring(0, 32).toByteArray(), "AES")
		val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")!!
		cipher.init(cipherMode, secretKeySpec, ivParameterSpec)
		return cipher
	}
}