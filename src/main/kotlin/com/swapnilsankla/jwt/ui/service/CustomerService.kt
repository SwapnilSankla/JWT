package com.swapnilsankla.jwt.ui.service

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.model.CustomerNotFoundException
import com.swapnilsankla.jwt.ui.model.InvalidTokenException
import com.swapnilsankla.jwt.ui.model.UnknownProcessingError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class CustomerService(
	@Autowired private val restTemplate: RestTemplate,
	@Value("\${customer-service.get-customers.endpoint}") val customerServiceEndpoint: String
) {
	fun get(customerId: String, token: String): Customer {
		try {
			return restTemplate
				.exchange(
					customerServiceEndpoint.replace("{customerId}", customerId),
					HttpMethod.GET,
					HttpEntity(null, httpHeaders(token)),
					Customer::class.java
				)
				.body ?: throw CustomerNotFoundException("No customer exists for given id")
		} catch (exception: HttpClientErrorException) {
			if(exception.statusCode == HttpStatus.NOT_FOUND)
				throw CustomerNotFoundException("No customer exists for given id")
			else if(exception.statusCode == HttpStatus.UNAUTHORIZED)
				throw InvalidTokenException("User is cannot be authorized with given token")
		} catch (exception: Exception) {
			throw UnknownProcessingError("Unknown error", exception)
		}
		throw UnknownProcessingError("Unknown error")
	}

	private fun httpHeaders(token: String): HttpHeaders {
		val headers = HttpHeaders()
		headers.set("authorization", token)
		return headers
	}
}