package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class UIController(@Autowired private val customerService: CustomerService) {
	@GetMapping("/ui/customers/{customerId}")
	fun getCustomerDetails(@PathVariable customerId: String): Customer {
		return customerService.get(customerId)
	}
}

