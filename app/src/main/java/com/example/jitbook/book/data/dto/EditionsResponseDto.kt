package com.example.jitbook.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditionsResponseDto(
    @SerialName("entries") val entries: List<EditionDto>
)
