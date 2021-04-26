package com.swapnilsankla.jwt.customer.controller

import com.swapnilsankla.jwt.customer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
	@Autowired private val customers: List<Customer>,
	@Autowired private val authService: AuthService
) {

	@GetMapping("/{customerId}")
	fun get(@PathVariable customerId: String, @RequestHeader("authorization") token: String): ResponseEntity<Customer> {
		if (!authService.isTokenValid(token)) return ResponseEntity.status(UNAUTHORIZED).build()
		val customer: Customer = customers.find { it.id == customerId } ?: return ResponseEntity.notFound().build()
		return ResponseEntity.ok(customer)
	}
}

