package com.swapnilsankla.jwt.auth.service

import java.security.Key


interface Encryption {
	fun key(): Key
}