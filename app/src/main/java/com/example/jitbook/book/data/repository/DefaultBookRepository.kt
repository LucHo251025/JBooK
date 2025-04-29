package com.example.jitbook.book.data.repository

import com.example.jitbook.book.data.mappers.toBook
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.network.RemoteBookDataSource
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result
import com.example.jitbook.core.domain.map


class DefaultBookRepository (
    private val remoteBookDataSource: RemoteBookDataSource,
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>,DataError.Remote> {
        return  remoteBookDataSource
            .searchBook(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }


    }

}