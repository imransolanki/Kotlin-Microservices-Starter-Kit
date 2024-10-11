package org.edu.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pet (

    @SerialName("id"        ) var id        : Long?,
    @SerialName("name"      ) var name      : String,
    @SerialName("photoUrl"  ) var photoUrl  : String,
    @SerialName("status"    ) var status    : String

)
