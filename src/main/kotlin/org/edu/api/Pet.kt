package org.edu.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Pet (

    @SerialName("id"        ) var id        : Long?,
    @SerialName("name"      ) var name      : String,
    @SerialName("photoUrl"  ) var photoUrl  : String,
    @SerialName("status"    ) var status    : String

)
