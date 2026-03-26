package org.edu.api

class NotFoundException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)
