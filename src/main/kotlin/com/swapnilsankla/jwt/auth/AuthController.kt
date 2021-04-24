package com.swapnilsankla.jwt.auth

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {
	companion object {
		private const val TOKEN = "token"
	}

	@PostMapping("/token")
	fun token(): String {
		return TOKEN
	}

	@GetMapping("/validate/{token}")
	fun validateToken(@PathVariable token: String): Boolean {
		return token == TOKEN
	}
}