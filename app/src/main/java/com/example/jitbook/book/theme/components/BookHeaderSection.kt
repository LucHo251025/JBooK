package com.example.jitbook.book.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jitbook.book.theme.JITBookTheme


@Composable
fun BookHeaderSection(
    title: String,
    imageUrl: String,
    authors: List<String>,
    firstPublishYear: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
            )
            .padding(4.dp)


    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier = Modifier
                .width(150.dp)
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop

        )
        Spacer(modifier = Modifier.width(16.dp))

        BookInfo(
            title = title,
            authors = authors,
            firstPublishYear = firstPublishYear,
            modifier = Modifier
                .fillMaxWidth()
        )


    }
}

@Composable
private fun BookInfo(
    title: String,
    authors: List<String>,
    firstPublishYear: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = authors.joinToString(", "),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))

        // Năm phát hành
        firstPublishYear?.let {
            Text(
                text = "Released on $it",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun BookHeaderSectionPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        BookHeaderSection(
            title = "Book Title TitleTitleTitleTitleTitleTitleTitleTitleTitleTitleTitle Title Title Title Title Title Title Title Title",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            authors = listOf("Author 1", "Author 2"),
            firstPublishYear = "2023"
        )
    }
}