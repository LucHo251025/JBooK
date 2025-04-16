package com.example.jitbook.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.data.model.Book
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.BookCarousel
import com.example.jitbook.ui.theme.components.FallingDots
import com.example.jitbook.ui.theme.navigation.BookContentType


@Composable
fun HomePageScreen(
    contentType: BookContentType,
    modifier: Modifier = Modifier
) {
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
        when(contentType) {
            BookContentType.LIST_ONLY -> {
                HomePageContent(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            BookContentType.LIST_AND_DETAIL -> {
                HomePageContent(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun HomePageContent(
    modifier: Modifier = Modifier
) {
    val sampleBooks = listOf(
        Book(
            title = "Avengers - Infinity War",
            imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            genres = "Action, Adventure, Sci-fi",
            rating = 4.8,
            ratingCount = 1222
        ),
        Book(
            title = "Interstellar",
            imageUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            genres = "Sci-fi, Drama",
            rating = 4.6,
            ratingCount = 982
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        ),
        Book(
            title = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            genres = "Action, Crime, Drama",
            rating = 4.9,
            ratingCount = 1520
        )

    )
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),

    ) {
        BookCarousel(sampleBooks)
    }

}

@Preview(showBackground = true)
@Composable
fun HomePageScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        HomePageScreen(
            contentType = BookContentType.LIST_ONLY,
        )
    }
}


@Preview(
    name = "Compact (Phone Portrait)",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun CompactHomePageScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        HomePageScreen(
            contentType = BookContentType.LIST_ONLY,
        )
    }
}

@Preview(
    name = "Medium (Tablet Portrait)",
    showBackground = true,
    widthDp = 600,
    heightDp = 800
)
@Composable
fun MediumHomePageScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
      HomePageScreen(
            contentType = BookContentType.LIST_ONLY,
      )
    }
}


@Preview(
    name = "Expanded (Tablet Landscape)",
    showBackground = true,
    widthDp = 1000,
    heightDp = 800
)
@Composable
fun ExpandedHomePageScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {

        HomePageScreen(
            contentType = BookContentType.LIST_AND_DETAIL,
        )
    }
}