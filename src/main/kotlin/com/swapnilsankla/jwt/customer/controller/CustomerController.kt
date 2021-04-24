package com.swapnilsankla.jwt.customer.controller

import com.swapnilsankla.jwt.customer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(@Autowired private val customers: List<Customer>) {

	@GetMapping("/{customerId}")
	fun get(@PathVariable customerId: String): ResponseEntity<Customer> {
		val customer: Customer = customers.find { it.id == customerId } ?: return ResponseEntity.notFound().build()
		return ResponseEntity.ok(customer)
	}
}

