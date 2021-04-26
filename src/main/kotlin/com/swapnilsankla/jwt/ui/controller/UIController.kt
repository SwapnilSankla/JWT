package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.service.AuthServiceInUIPackage
import com.swapnilsankla.jwt.ui.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ui")
class UIController(
	@Autowired private val customerService: CustomerService,
	@Autowired private val authService: AuthServiceInUIPackage
) {
	@GetMapping("/customers/{customerId}")
	fun getCustomerDetails(
		@PathVariable customerId: String,
		@Nullable @RequestHeader("authorization") receivedToken: String?
	): ResponseEntity<Customer> {
		if(receivedToken != null) {
			val isCustomerAuthorized = authService.isUserAuthorised(receivedToken, customerId)
			if(!isCustomerAuthorized)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		}
		val token = receivedToken ?: authService.generateToken(customerId)

		val customer = customerService.get(customerId, token)

		return ResponseEntity
			.ok()
			.header("authorization", token)
			.body(customer)
	}
}

