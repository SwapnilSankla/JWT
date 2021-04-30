package com.swapnilsankla.jwt.authvalidator

import com.swapnilsankla.jwt.auth.service.KeyStoreProperties
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.security.KeyStore
import java.security.PrivateKey
import java.util.*
import javax.crypto.Cipher

@Component
class TokenResolver(
	@Autowired private val signingKeyResolver: SigningKeyResolver,
	@Autowired val tokenResolverKeyStoreProperties: TokenResolverKeyStoreProperties
) {
	fun getClaims(token: String): Map<String, Any> {
		val encryptedClaims = (parseToken(token)?.body as? Claims)?.toMap() ?: emptyMap()
		return encryptedClaims.mapValues { if(it.key == "customerId") decryptClaim(it.value as String) else it.value}
	}

	fun validateTokenWithPublicKey(token: String): Boolean {
		return try {
			parseToken(token)
			true
		} catch (exception: Exception) {
			false
		}
	}

	private fun decryptClaim(encryptedClaim: String): String {
		val cipher = cipherInstance(Cipher.DECRYPT_MODE)
		val original = cipher.doFinal(Base64.getDecoder().decode(encryptedClaim))
		return String(original)
	}

	private fun cipherInstance(cipherMode: Int): Cipher {
		val cipher = Cipher.getInstance("RSA")!!
		cipher.init(cipherMode, privateKey())
		return cipher
	}

	private fun privateKey(): PrivateKey {
		val resourceStream = ResourceUtils.getFile(tokenResolverKeyStoreProperties.path).inputStream()
		val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
		keyStore.load(resourceStream, tokenResolverKeyStoreProperties.password.toCharArray())
		return keyStore.getKey(tokenResolverKeyStoreProperties.alias, tokenResolverKeyStoreProperties.password.toCharArray()) as PrivateKey
	}


	private fun parseToken(token: String): Jwt<out Header<*>, *>? {
		return Jwts.parserBuilder().setSigningKeyResolver(signingKeyResolver).build().parse(token)
	}
}