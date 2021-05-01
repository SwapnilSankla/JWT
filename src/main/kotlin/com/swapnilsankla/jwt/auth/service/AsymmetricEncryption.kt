package com.swapnilsankla.jwt.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.security.Key
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.CertificateFactory
import java.util.*
import javax.crypto.Cipher

@Component
class AsymmetricEncryption(@Autowired val keyStoreProperties: KeyStoreProperties): Encryption {
	override fun signingKey(): Key {
		val resourceStream = ResourceUtils.getFile(keyStoreProperties.path).inputStream()
		val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
		keyStore.load(resourceStream, keyStoreProperties.password.toCharArray())
		return keyStore.getKey(keyStoreProperties.alias, keyStoreProperties.password.toCharArray()) as PrivateKey
	}

	override fun encrypt(claim: String): String {
		val cipher = cipherInstance(Cipher.ENCRYPT_MODE)
		val encrypted = cipher.doFinal(claim.toByteArray())
		return Base64.getEncoder().encodeToString(encrypted)
	}

	override fun decrypt(encryptedClaim: String): String {
		TODO("Not yet implemented")
	}

	private fun publicKey(): PublicKey {
		val publicCertStream = ResourceUtils.getFile("classpath:jwtConsumerPublicKey.cer").inputStream()
		return CertificateFactory.getInstance("X.509").generateCertificate(publicCertStream).publicKey
	}

	private fun cipherInstance(cipherMode: Int): Cipher {
		val cipher = Cipher.getInstance("RSA")!!
		cipher.init(cipherMode, publicKey())
		return cipher
	}
}