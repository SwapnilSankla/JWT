package com.swapnilsankla.jwt.ui.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class UIConfigurationClass {
	@Bean
	open fun restTemplate() = RestTemplate()

	@Bean
	open fun objectMapper() = ObjectMapper().registerModule(KotlinModule())
}