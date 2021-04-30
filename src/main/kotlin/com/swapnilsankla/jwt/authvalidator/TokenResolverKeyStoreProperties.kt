package com.swapnilsankla.jwt.authvalidator

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("token-resolver.key-store")
open class TokenResolverKeyStoreProperties {
	lateinit var path: String
	lateinit var password: String
	lateinit var alias: String
}