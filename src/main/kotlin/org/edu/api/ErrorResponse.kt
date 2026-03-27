package org.edu.api

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String)
