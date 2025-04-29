package com.example.jitbook.book.data.network

import androidx.room.Query
import com.example.jitbook.book.data.dto.SearchResponseDto
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBook(
        query: String,
        resultLimit: Int? = null,
    ): Result<SearchResponseDto, DataError.Remote>
}