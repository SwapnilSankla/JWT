package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.Customer
import com.swapnilsankla.jwt.ui.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("")
class UIController(@Autowired private val customerService: CustomerService) {
	@GetMapping("/ui/customers/{customerId}")
	fun getCustomerDetails(@PathVariable customerId: String, @Nullable @RequestHeader("authorization") token: String?): Customer {
		return customerService.get(customerId, token)
	}
}

