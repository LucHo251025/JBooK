package com.example.jitbook.book.theme

import coil.compose.AsyncImagePainter
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.model.Rating

data class BookDetailState (
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val book: Book? = null,
    val ratings: List<Rating> = emptyList(),
    val averageRating: Double = 0.0,
    val numReviews: Int = 0
)