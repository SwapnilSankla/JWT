package com.swapnilsankla.jwt.auth.service

import com.swapnilsankla.jwt.microtype.TokenSigningMethod
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class EncryptionFactory(@Value("\${token.signing.method}") val tokenSigningMethod: String,
												@Autowired val symmetricEncryption: SymmetricEncryption,
												@Autowired val asymmetricEncryption: AsymmetricEncryption,) {
	fun get(): Encryption {
		return when (tokenSigningMethod) {
			TokenSigningMethod.Symmetric.name -> symmetricEncryption
			else -> asymmetricEncryption
		}
	}
}