package com.example.jitbook.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectBooksResponseDto(
    @SerialName("work_count") val total: Int,
    @SerialName("works") val books: List<SubjectBookDto>
)