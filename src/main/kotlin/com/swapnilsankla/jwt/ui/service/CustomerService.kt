package com.swapnilsankla.jwt.ui.service

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.model.CustomerNotFoundException
import com.swapnilsankla.jwt.ui.model.UnknownProcessingError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class CustomerService(@Autowired private val restTemplate: RestTemplate) {
	fun get(customerId: String): Customer {
		try {
			return restTemplate
				.getForEntity(URI.create("http://localhost:8081/customers/$customerId"), Customer::class.java).body!!
		} catch (exception: HttpClientErrorException) {
			if(exception.statusCode == HttpStatus.NOT_FOUND)
				throw CustomerNotFoundException("No customer exists for given id")
		} catch (exception: Exception) {
			throw UnknownProcessingError("Unknown error", exception)
		}
		throw UnknownProcessingError("Unknown error")
	}
}