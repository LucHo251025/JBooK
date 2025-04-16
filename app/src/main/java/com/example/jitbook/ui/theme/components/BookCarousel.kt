package com.example.jitbook.ui.theme.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.data.model.Book
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookCarousel(books: List<Book>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    // Tính toán width và imageHeight dựa vào kích thước màn hình
    val cardWidth = screenWidthDp * 0.6f // Chiếm 60% chiều rộng màn hình
    val imageHeight = screenHeightDp * 0.35f // Chiếm 35% chiều cao màn hình
    if (books.isNotEmpty()) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(6000L)
                val nextPage = (pagerState.currentPage + 1) % books.size
                pagerState.animateScrollToPage(
                    nextPage,
                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                )
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Now Playing",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        HorizontalPager(
            count = books.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 64.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val currentPage = pagerState.currentPage
            val offset = pagerState.currentPageOffset
            val pageOffset = (currentPage - page) + offset

            // Scale & alpha transform
            val scale = 1f - (pageOffset.absoluteValue * 0.2f).coerceIn(0f, 1f)
            val alpha = 1f - (pageOffset.absoluteValue * 0.5f).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .padding(8.dp)
            ) {
                BookCard(
                    Book(
                        title = books[page].title,
                        imageUrl = books[page].imageUrl,
                        genres = books[page].genres,
                        rating = books[page].rating,
                        ratingCount = books[page].ratingCount,

                        ),
                    width = cardWidth,
                    imageHeight = imageHeight,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color.Yellow,
            inactiveColor = Color.Gray
        )
    }
}

@Preview
@Composable
fun BookCarouselPreview() {
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

    BookCarousel(
        sampleBooks,
    )
}
