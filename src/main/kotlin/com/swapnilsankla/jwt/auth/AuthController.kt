package com.swapnilsankla.jwt.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {
	@PostMapping("/token")
	fun token(): String {
		return "token"
	}
}