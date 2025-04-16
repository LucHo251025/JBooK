package com.example.jitbook.ui.theme.components

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
import com.example.jitbook.data.model.Book
import com.example.jitbook.ui.theme.JITBookTheme

@Composable
fun BookSection(
    title: String,
    books: List<Book>,
    onAllClick: () -> Unit = {},
    onBookClick: (Book) -> Unit = {},
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
        BookSection(
            title = "Popular Books",
            books = listOf(
                Book(
                    title = "Avengers - Infinity War",
                    imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                    genres = "Action, Adventure, Sci-fi",
                    rating = 4.8,
                    ratingCount = 1222
                ),
                Book(
                    title = "Avengers - Endgame",
                    imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                    genres = "Action, Adventure, Sci-fi",
                    rating = 4.8,
                    ratingCount = 1222
                ),
                Book(
                    title = "Avengers - Endgame",
                    imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                    genres = "Action, Adventure, Sci-fi",
                    rating = 4.8,
                    ratingCount = 1222
                ),
                Book(
                    title = "Avengers - Endgame",
                    imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                    genres = "Action, Adventure, Sci-fi",
                    rating = 4.8,
                    ratingCount = 1222
                ),
                Book(
                    title = "Avengers - Endgame",
                    imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                    genres = "Action, Adventure, Sci-fi",
                    rating = 4.8,
                    ratingCount = 1222
                ),
            ),
            onBookClick = {}
        )
    }
}
