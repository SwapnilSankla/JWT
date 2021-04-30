package com.swapnilsankla.jwt.auth.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.security.Key
import java.security.KeyStore
import java.security.PrivateKey

@Component
class AsymmetricEncryption(@Autowired val keyStoreProperties: KeyStoreProperties): Encryption {
	override fun key(): Key {
		val resourceStream = ResourceUtils.getFile(keyStoreProperties.path).inputStream()
		val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
		keyStore.load(resourceStream, keyStoreProperties.password.toCharArray())
		return keyStore.getKey(keyStoreProperties.alias, keyStoreProperties.password.toCharArray()) as PrivateKey
	}
}