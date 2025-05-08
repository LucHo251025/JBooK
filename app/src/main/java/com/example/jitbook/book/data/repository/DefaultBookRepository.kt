package com.example.jitbook.book.data.repository

import com.example.jitbook.book.data.mappers.toBook
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.model.Rating
import com.example.jitbook.book.data.network.RemoteBookDataSource
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result
import com.example.jitbook.core.domain.map


class DefaultBookRepository (
    private val remoteBookDataSource: RemoteBookDataSource,
//    private val firebaseBookDataSource: FirebaseBookDataSource
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>,DataError.Remote> {
        return  remoteBookDataSource
            .searchBook(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }


    }
    override suspend fun getBookDescription(bookId: String): Result<String?, DataError.Remote> {
        return remoteBookDataSource
            .getBookDetails(bookId)
            .map { it.description }
    }
    override suspend fun getReadableLink(bookWorkId: String): Result<String?, DataError.Remote> {
        return remoteBookDataSource.getEditions(bookWorkId).map { editionResponse ->
            val ocaid = editionResponse.entries.firstOrNull { it.ocaid != null }?.ocaid
            ocaid?.let { "https://archive.org/stream/$it" }
        }
    }
    override suspend fun getBooksBySubject(subject: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.getBooksBySubject(subject).map { dto ->
            dto.books.map { it.toBook() }
        }
    }

    override suspend fun getFavoriteBooks(bookIds: List<String>): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.getFavoriteBooksByIds(bookIds).map { dtoList ->
            dtoList.map { it.toBook() }
        }
    }

//    override suspend fun getBookRating(bookId: String): Result<Float, DataError.Remote> {
//        return try {
//            val ratings = firebaseBookDataSource.getRatings(bookId)
//            val avg = if (ratings.isNotEmpty()) ratings.map { it.rating }.average().toFloat() else 0f
//            Result.Success(avg)
//        } catch (e: Exception) {
//            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
//        }
//    }
//
//    override suspend fun getBookComments(bookId: String): Result<List<Rating>, DataError.Remote> {
//        return try {
//            val comments = firebaseBookDataSource.getRatings(bookId)
//            Result.Success(comments)
//        } catch (e: Exception) {
//            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
//        }
//    }
//
//    override suspend fun submitBookRating(bookId: String, rating: Float, comment: String): Result<Unit, DataError.Remote> {
//        return try {
//            firebaseBookDataSource.submitRating(bookId, rating, comment)
//            Result.Success(Unit)
//        } catch (e: Exception) {
//            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
//        }
//    }
//
//    override suspend fun isFavorite(bookId: String): Result<Boolean, DataError.Remote> {
//        return try {
//            val result = firebaseBookDataSource.isFavorite(bookId)
//            Result.Success(result)
//        } catch (e: Exception) {
//            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
//        }
//    }
//
//    override suspend fun toggleFavorite(bookId: String): Result<Boolean, DataError.Remote> {
//        return try {
//            val result = firebaseBookDataSource.toggleFavorite(bookId)
//            Result.Success(result)
//        } catch (e: Exception) {
//            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
//        }
//    }
}