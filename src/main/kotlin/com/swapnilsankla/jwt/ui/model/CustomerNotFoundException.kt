package com.swapnilsankla.jwt.ui.model

class CustomerNotFoundException(msg: String) : Throwable(msg)

class InvalidTokenException(msg: String) : Throwable(msg)

class UnknownProcessingError(msg: String, cause: Throwable? = null): Throwable(msg, cause)

class FailedToGenerateTokenException(msg: String): Throwable(msg)