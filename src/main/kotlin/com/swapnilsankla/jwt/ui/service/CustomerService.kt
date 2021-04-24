package com.swapnilsankla.jwt.ui.service

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.model.CustomerNotFoundException
import com.swapnilsankla.jwt.ui.model.InvalidTokenException
import com.swapnilsankla.jwt.ui.model.UnknownProcessingError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class CustomerService(@Autowired private val restTemplate: RestTemplate) {
	fun get(customerId: String, token: String?): Customer {
		try {
			return restTemplate
				.exchange(
					"http://localhost:8081/customers/$customerId",
					HttpMethod.GET,
					HttpEntity(null, httpHeaders(token ?: generateToken())),
					Customer::class.java
				)
				.body!!
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

	private fun generateToken(): String {
		return restTemplate
			.postForEntity("http://localhost:8081/auth/token", null, String::class.java).body!!
	}

	private fun httpHeaders(token: String): HttpHeaders {
		val headers = HttpHeaders()
		headers.set("authorization", token)
		return headers
	}
}