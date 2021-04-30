package com.swapnilsankla.jwt.authvalidator

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwsHeader
import io.jsonwebtoken.SigningKeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils
import java.security.Key
import java.security.cert.CertificateFactory

@Configuration
open class ConfigurationClassInAuthValidator {
	@Bean
	open fun signingKeyResolver(): SigningKeyResolver {
		return object : SigningKeyResolver {
			val publicKey = publicKey()
			override fun resolveSigningKey(header: JwsHeader<out JwsHeader<*>>?, claims: Claims?): Key {
				return publicKey
			}

			override fun resolveSigningKey(header: JwsHeader<out JwsHeader<*>>?, plaintext: String?): Key {
				return publicKey
			}

			private fun publicKey(): Key {
				val publicCertStream = ResourceUtils.getFile("classpath:publicKey.cer").inputStream()
				return CertificateFactory.getInstance("X.509").generateCertificate(publicCertStream).publicKey
			}
		}
	}
}