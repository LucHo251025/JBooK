package com.example.jitbook.book.data.network

import androidx.room.Query
import com.example.jitbook.book.data.dto.BookWorkDto
import com.example.jitbook.book.data.dto.EditionsResponseDto
import com.example.jitbook.book.data.dto.FavoriteBookDto
import com.example.jitbook.book.data.dto.FavoriteRepositoryDto
import com.example.jitbook.book.data.dto.SearchResponseDto
import com.example.jitbook.book.data.dto.SubjectBooksResponseDto
import com.example.jitbook.core.data.safeCall
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBook(
        query: String,
        resultLimit: Int? = null,
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
    suspend fun getEditions(bookWorkId: String): Result<EditionsResponseDto, DataError.Remote>
    suspend fun getBooksBySubject(subject: String): Result<SubjectBooksResponseDto, DataError.Remote>
    suspend fun getFavoriteBooksByIds(bookIds: List<String>): Result<List<FavoriteBookDto>, DataError.Remote>

}