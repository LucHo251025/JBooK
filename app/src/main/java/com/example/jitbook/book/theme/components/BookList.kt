package com.example.jitbook.book.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.data.model.Book


@Composable
fun BookList(
    books: List<com.example.jitbook.book.data.model.Book>,
    onBookClick: (com.example.jitbook.book.data.model.Book) -> Unit,
    modifier: Modifier = Modifier)
{
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()

    ) {
        items(books.size) { index ->
            BookCard(
                com.example.jitbook.book.data.model.Book(
                    id = books[index].id,
                    title = books[index].title,
                    imageUrl = books[index].imageUrl,
                    authors = books[index].authors,
                    description = books[index].description,
                    languages = books[index].languages,
                    firstPublishYear = books[index].firstPublishYear,
                    averageRating = books[index].averageRating,
                    ratingsCount = books[index].ratingsCount,
                    numPages = books[index].numPages,
                    numEdition = books[index].numEdition

                ),
                modifier = modifier
                    .clickable(
                        onClick = { onBookClick(books[index]) }
                    ),
                width = 220.dp,
                imageHeight = 200.dp,
            )
        }
    }

}

@Preview
@Composable
fun BookListPreview() {
    val sampleBooks = listOf(
        com.example.jitbook.book.data.model.Book(
            id =1,
            title = "Avengers - Infinity War",
            imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10
        ),
        com.example.jitbook.book.data.model.Book(
            id = 2,
            title = "Interstellar",
            imageUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10
        ),
        com.example.jitbook.book.data.model.Book(
            id = 3,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10
        ),
        com.example.jitbook.book.data.model.Book(
            id = 4,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10
        ),
        com.example.jitbook.book.data.model.Book(
            id = 5,
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10,
        )
    )

    BookList(
        books = sampleBooks,
        onBookClick = { book ->
            // Handle book click
        },
    )

}

