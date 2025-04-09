package com.example.jitbook.ui.theme.components

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
import com.example.jitbook.data.model.Book


@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
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
                title = books[index].title,
                imageUrl = books[index].imageUrl,
                duration = books[index].duration,
                genres = books[index].genres,
                rating = books[index].rating,
                ratingCount = books[index].ratingCount,
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
        Book(
            title = "Avengers - Infinity War",
            imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            duration = "2h29m",
            genres = "Action, Adventure, Sci-fi",
            rating = 4.8,
            ratingCount = 1222
        ),
        Book(
            title = "Interstellar",
            imageUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            duration = "2h49m",
            genres = "Sci-fi, Drama",
            rating = 4.6,
            ratingCount = 982
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            duration = "2h32m",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            duration = "2h32m",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            duration = "2h32m",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        )

    )

    BookList(
        books = sampleBooks,
        onBookClick = { book ->
            // Handle book click
        },
    )

}

