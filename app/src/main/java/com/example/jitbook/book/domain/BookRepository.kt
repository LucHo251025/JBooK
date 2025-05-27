package com.example.jitbook.book.domain

import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.model.Rating
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String) : Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote>
    suspend fun getReadableLink(bookWorkId: String): Result<String?, DataError.Remote>
    suspend fun getBooksBySubject(subject: String): Result<List<Book>, DataError.Remote>
    suspend fun getFavoriteBooks(bookIds: List<String>): Result<List<Book>, DataError.Remote>
//    suspend fun getBookRating(bookId: String): Result<Float, DataError.Remote>
//    suspend fun getBookComments(bookId: String): Result<List<Rating>, DataError.Remote>
//    suspend fun submitBookRating(bookId: String, rating: Float, comment: String): Result<Unit, DataError.Remote>
//    suspend fun isFavorite(bookId: String): Result<Boolean, DataError.Remote>
//    suspend fun toggleFavorite(bookId: String): Result<Boolean, DataError.Remote>
}