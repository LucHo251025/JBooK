package com.example.jitbook.book.data.network

import com.example.jitbook.book.data.dto.SearchResponseDto
import com.example.jitbook.core.data.safeCall
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
) {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                  parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_count,number_of_pages_median,edition_count"
                )

            }
        }
    }
}