package com.swapnilsankla.jwt.customer.controller

import com.swapnilsankla.jwt.customer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/customers")
class CustomerController(
	@Autowired private val customers: List<Customer>,
	@Autowired private val restTemplate: RestTemplate
) {

	@GetMapping("/{customerId}")
	fun get(@PathVariable customerId: String, @RequestHeader("authorization") token: String): ResponseEntity<Customer> {
		if (!isTokenValid(token)) return ResponseEntity.status(UNAUTHORIZED).build()
		val customer: Customer = customers.find { it.id == customerId } ?: return ResponseEntity.notFound().build()
		return ResponseEntity.ok(customer)
	}

	private fun isTokenValid(token: String) =
		restTemplate.getForEntity("http://localhost:8081/auth/validate/$token", Boolean::class.java).body ?: false
}

