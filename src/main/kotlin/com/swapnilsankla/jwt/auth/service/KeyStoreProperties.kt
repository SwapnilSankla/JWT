package com.swapnilsankla.jwt.auth.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("key-store")
open class KeyStoreProperties {
	lateinit var path: String
	lateinit var password: String
	lateinit var alias: String
}