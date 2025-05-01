package com.example.jitbook.book.data.mappers

import com.example.jitbook.book.data.dto.SearchedBookDto
import com.example.jitbook.book.data.model.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id =  id,
        title = title,
        imageUrl =if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        }else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numPagesMedian,
        numEdition = numEdition?: 0,
    )
}