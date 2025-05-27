package com.example.jitbook.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditionDto(
    @SerialName("ocaid") val ocaid: String? = null
)
