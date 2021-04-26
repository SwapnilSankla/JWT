package com.swapnilsankla.jwt.auth

import com.swapnilsankla.jwt.auth.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(@Autowired val tokenService: TokenService){
	@PostMapping("/token")
	fun token() = tokenService.generate()

	@GetMapping("/validate/{token}")
	fun validateToken(@PathVariable token: String) = tokenService.validate(token)
}