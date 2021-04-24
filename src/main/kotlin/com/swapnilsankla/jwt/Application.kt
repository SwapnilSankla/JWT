package com.swapnilsankla.jwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}

@SpringBootApplication
open class Application