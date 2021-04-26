package com.swapnilsankla.jwt.ui.controller

import com.swapnilsankla.jwt.ui.model.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UIControllerAdvice {
	@ExceptionHandler(CustomerNotFoundException::class)
	fun handleCustomerNotFound(exception: CustomerNotFoundException): ResponseEntity<Customer> {
		return ResponseEntity.notFound().build()
	}

	@ExceptionHandler(UnknownProcessingError::class)
	fun handleCustomerNotFound(exception: UnknownError): ResponseEntity<Customer> {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
	}

	@ExceptionHandler(InvalidTokenException::class, FailedToGenerateTokenException::class)
	fun handleCustomerNotFound(exception: InvalidTokenException): ResponseEntity<Customer> {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
	}
}