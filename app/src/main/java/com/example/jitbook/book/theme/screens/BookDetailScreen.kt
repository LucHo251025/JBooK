package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookDescription
import com.example.jitbook.book.theme.components.BookHeaderSection
import com.example.jitbook.book.theme.components.BookInfo
import com.example.jitbook.book.theme.components.BookRating
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.PrimaryButton

@Composable
fun BookDetailScreen(
    book: Book,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )
        Column(
            modifier = modifier
                .verticalScroll(scrollState) // Thêm modifier này
                .padding(16.dp)
        ) {

            BookHeaderSection(
                title = book.title,
                imageUrl = book.imageUrl,
                authors = book.authors,
                firstPublishYear = book.firstPublishYear,
                modifier = modifier
            )
            Spacer(modifier = Modifier.height(10.dp))

            BookInfo(
                averageRating = book.averageRating,
                ratingsCount = book.ratingsCount,
                numPages = book.numPages,
                numEdition = book.numEdition,
            )
            Spacer(modifier = Modifier.height(15.dp))
            PrimaryButton(
                text = "Read Book",
                onClick = { /* Handle read book click */ },
                modifier = modifier
                    .fillMaxSize()
                    .height(55.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            BookDescription(
                book.description
            )
            Spacer(modifier = Modifier.height(19.dp))
            Divider(
                color = Color(0xFFD9CFCF),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(19.dp))
            BookRating(
                onSubmitClicked = { rating, description ->
                    println("Rating: $rating, Description: $description")
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        BookDetailScreen(
            book = Book(
                id = "1",
                title = "Title Book Title Sample Book Title",
                imageUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
                authors = listOf("Author One", "Author Two"),
                description = "This is a sample This is a sample description o This is a sample description description description o This is  a sample sample sample sample sample sample sample description o description of the book.",
                languages = listOf("English"),
                firstPublishYear = "2023",
                averageRating = 4.5,
                ratingsCount = 100,
                numPages = 300,
                numEdition = 1
            )
        )
    }

}
