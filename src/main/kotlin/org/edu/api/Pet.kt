package org.edu.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Pet (

    @SerialName("id"        ) var id        : Int,
    @SerialName("name"      ) var name      : String,
    @SerialName("photoUrls" ) var photoUrls : List<String> = arrayListOf(),
    @SerialName("status"    ) var status    : String

)
