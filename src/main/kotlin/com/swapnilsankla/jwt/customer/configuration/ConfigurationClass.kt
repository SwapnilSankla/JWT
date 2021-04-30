package com.swapnilsankla.jwt.customer.configuration

import com.swapnilsankla.jwt.customer.model.Customer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class ConfigurationClass {
	@Bean
	open fun customers(): List<Customer> {
		return listOf(Customer("1", "Swapnil"), Customer("2", "Supriya"))
	}

	@Bean
	open fun restTemplateInCustomer() = RestTemplate()
}