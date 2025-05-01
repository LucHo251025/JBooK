package com.example.jitbook.book.theme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.JITBookTheme

@Composable
fun BookSection(
    title: String,
    books: List<com.example.jitbook.book.data.model.Book>,
    onAllClick: () -> Unit = {},
    onBookClick: (com.example.jitbook.book.data.model.Book) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary
            )

            IconButton(onClick = onAllClick) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(books) { book ->
                BookCard(
                    book = book,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .weight(1f),
                    width = 170.dp,
                    imageHeight = 190.dp,
                )
            }
        }
    }
}

@Preview
@Composable
fun BookSectionPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        val sampleBooks = listOf(
            com.example.jitbook.book.data.model.Book(
                id ="1",
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

        BookSection(
            title = "Popular Books",
            books = sampleBooks,
            onBookClick = {}
        )
    }
}
