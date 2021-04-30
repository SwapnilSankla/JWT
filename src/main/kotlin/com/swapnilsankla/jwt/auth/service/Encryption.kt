package com.swapnilsankla.jwt.auth.service

import java.security.Key


interface Encryption {
	fun signingKey(): Key
	fun encrypt(claim: String): String
	fun decrypt(encryptedClaim: String): String
}