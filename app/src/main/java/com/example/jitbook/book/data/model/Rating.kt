package com.example.jitbook.book.data.model

import java.sql.Timestamp

data class Rating(
    val reviewId: String? = "",
    val userId: String? = "",
    val userName: String? = "",
    val imageUrl: String? = "",
    val rating: Int? = 0,
    val comment: String? = "",
    val timestamp: Timestamp? = null
)