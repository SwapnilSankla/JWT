package com.swapnilsankla.jwt.auth

import com.swapnilsankla.jwt.auth.service.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.lang.NonNull
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(@Autowired val tokenService: TokenService){
	@PostMapping("/token")
	fun token(@RequestBody @NonNull request: GenerateTokenRequest) = tokenService.generate(request.customerId)

	@GetMapping("/token/{token}/isValid")
	fun validateToken(@PathVariable @NonNull token: String) = tokenService.validate(token)

	@GetMapping("/token/{token}/claims")
	fun claimsFromToken(@PathVariable @NonNull token: String) = tokenService.claimsFromToken(token)
}

data class GenerateTokenRequest(val customerId: String)