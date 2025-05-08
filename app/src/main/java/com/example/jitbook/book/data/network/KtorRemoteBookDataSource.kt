package com.example.jitbook.book.data.network

import com.example.jitbook.book.data.dto.BookWorkDto
import com.example.jitbook.book.data.dto.EditionsResponseDto
import com.example.jitbook.book.data.dto.FavoriteBookDto
import com.example.jitbook.book.data.dto.FavoriteRepositoryDto
import com.example.jitbook.book.data.dto.SearchResponseDto
import com.example.jitbook.book.data.dto.SubjectBooksResponseDto
import com.example.jitbook.core.data.safeCall
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {
    override suspend fun searchBook(
        query: String,
        resultLimit: Int?,
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
                )

            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }
    override suspend fun getEditions(bookWorkId: String): Result<EditionsResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/works/$bookWorkId/editions.json")
        }
    }

   override suspend fun getBooksBySubject(subject: String): Result<SubjectBooksResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get("$BASE_URL/subjects/$subject.json") {
                parameter("limit", 40)
            }
        }
    }




    override suspend fun getFavoriteBooksByIds(bookIds: List<String>): Result<List<FavoriteBookDto>, DataError.Remote> {
        val favoriteBooks = mutableListOf<FavoriteBookDto>()

        for (id in bookIds) {
            val result = safeCall<FavoriteRepositoryDto> {
                httpClient.get("$BASE_URL/search.json") {
                    parameter("q", "work:$id")
                    parameter("limit", 1)
                    parameter(
                        "fields",
                        "key,title,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count"
                    )
                }
            }

            when (result) {
                is Result.Success -> {
                    val doc = result.data.docs.firstOrNull()
                    if (doc != null) {
                        favoriteBooks.add(doc)
                    }
                }

                is Result.Error -> return Result.Error(result.error)
            }
        }

        return Result.Success(favoriteBooks)
    }

}