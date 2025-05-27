package com.example.jitbook.book.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jitbook.R
import kotlin.math.round


@Composable
fun BookCard(
    book: com.example.jitbook.book.data.model.Book,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    width: Dp = 220.dp,
    imageHeight: Dp = 250.dp,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(width)
                .height(imageHeight),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black),

            ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {


                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = book.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageHeight)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_break),
                    error = painterResource(id = R.drawable.image_break)
                )


            }
        }

        Column(
            modifier = Modifier
                .padding(top = 6.dp)
                .width(width),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            // Tên phim
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondary,
            )


            Spacer(modifier = Modifier.height(4.dp))

            // Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${round((book.averageRating ?: 0.0) * 10) / 10.0}",
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(4.dp))
                book.authors.firstOrNull()?.let { authorName ->
                    Text(
                        text = authorName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }

}


@Preview
@Composable
fun BookCardPreview() {
    BookCard(
        com.example.jitbook.book.data.model.Book(
            id = "1",
            title = "Book Title Example ",
            imageUrl = "https://covers.openlibrary.org/b/id/14826244-L.jpg",
            authors = listOf("F. Scott Fitzgerald"),
            description = "A novel about the American dream and the roaring twenties.",
            languages = listOf("English"),
            firstPublishYear = "1925",
            averageRating = 4.2,
            ratingsCount = 34567,
            numPages = 180,
            numEdition = 10
        )
    )
}