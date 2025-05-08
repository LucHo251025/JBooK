package com.example.jitbook.book.data.mappers

import com.example.jitbook.book.data.database.BookEntity
import com.example.jitbook.book.data.dto.FavoriteBookDto
import com.example.jitbook.book.data.dto.SearchedBookDto
import com.example.jitbook.book.data.dto.SubjectBookDto
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.model.Rating

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
fun FavoriteBookDto.toBook(): Book {
    return Book(
        id = id,
        title = title,
        imageUrl = if (coverKey != null)
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        else
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg",
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingsAverage,
        ratingsCount = ratingsCount,
        numPages = numPagesMedian,
        numEdition = numEdition ?: 0
    )
}

fun SubjectBookDto.toBook(): Book {
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

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        authors = authors,
        description = description,
        languages = languages,
        firstPublishYear = firstPublishYear,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        numPages = numPages,
        numEdition = numEdition
    )
}

fun Rating.toRating(): Rating {
    return Rating(
        rating = rating,
        comment = comment,
        userId = userId,
        imageUrl = imageUrl,
        timestamp = timestamp,
    )
}