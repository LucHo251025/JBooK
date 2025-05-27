//package com.example.jitbook.book.data.network
//
//import com.example.jitbook.book.data.model.Rating
//
//interface FirebaseBookDataSource {
//    suspend fun getRatings(bookId: String): List<Rating>
//    suspend fun submitRating(bookId: String, rating: Float, comment: String)
//    suspend fun isFavorite(bookId: String): Boolean
//    suspend fun toggleFavorite(bookId: String): Boolean
//}