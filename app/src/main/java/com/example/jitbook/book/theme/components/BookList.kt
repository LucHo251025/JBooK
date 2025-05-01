package com.example.jitbook.book.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.JITBookTheme


@Composable
fun BookList(
    books: List<com.example.jitbook.book.data.model.Book>,
    onBookClick: (com.example.jitbook.book.data.model.Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState = rememberLazyGridState(),
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)

    ) {
        items(
            items= books,
            key = {it.id}
        ) { book ->
            BookCard(
                book = book,
                modifier = modifier
                    .clickable(
                        onClick = { onBookClick(book) }
                    ),
                width = 145.dp,
                imageHeight = 210.dp,
            )
        }
    }

}

@Preview
@Composable
fun BookListPreview() {
    val sampleBooks = listOf(
        com.example.jitbook.book.data.model.Book(
            id = "1",
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
            id = "2",
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
            id = "3",
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
            id = "4",
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
            id = "5",
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

    JITBookTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        BookList(
            books = sampleBooks,
            onBookClick = { book ->
                // Handle book click
            },
        )
    }


}

