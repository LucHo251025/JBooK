package com.example.jitbook.book.domain

import com.example.jitbook.book.data.model.Book
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String) : Result<List<Book>, DataError.Remote>
}