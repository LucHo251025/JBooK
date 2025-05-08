package com.example.jitbook.book.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jitbook.R
import com.example.jitbook.book.theme.JITBookTheme
import kotlin.math.sinh


@Composable
fun BookHeaderSection(
    title: String,
    imageUrl: String,
    authors: List<String>,
    firstPublishYear: String?,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

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
            isFavorite = isFavorite,
            onFavoriteClick = onFavoriteClick,
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
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background
            )
            .fillMaxWidth()

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
            maxLines = 3,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Năm phát hành
        firstPublishYear?.let {
            Text(
                text = "Released on $it",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        IconToggleButton(
            checked = isFavorite,
            onCheckedChange = { onFavoriteClick() },
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .padding(8.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(id = R.string.add_to_favorite),
                tint = Color.White
            )
        }


    }
}

@Preview
@Composable
fun BookHeaderSectionPreview() {
    JITBookTheme(
        darkTheme = true,
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