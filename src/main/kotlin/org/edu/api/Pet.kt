package org.edu.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    val id: Long? = null,
    val name: String,
    @SerialName("photo_url") val photoUrl: String,
    val status: String,
)
