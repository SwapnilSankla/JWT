package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ui")
class UIController(@Autowired private val customerService: CustomerService) {
	@GetMapping("/customers/{customerId}")
	fun getCustomerDetails(@PathVariable customerId: String, @Nullable @RequestHeader("authorization") token: String?): ResponseEntity<Customer> {
		val token = customerService.token(token)
		val customer = customerService.get(customerId, token)
		return ResponseEntity
			.ok()
			.header("authorization", token)
			.body(customer)
	}
}

