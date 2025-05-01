package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookCarousel
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.navigation.BookContentType
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePageScreen(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        BookListScreen(
            state = state,
            onAction = { action ->
                when (action) {
                    is BookListAction.OnBookClick -> onBookClick(action.book)
                    else -> Unit
                }
                viewModel.onAction(action)
            },
            modifier = modifier
        )
    }
}

@Composable
private fun HomePage(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
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
        HomePageContent(
            modifier = Modifier
                .fillMaxSize()
        )
}
}

@Composable
fun HomePageContent(
    modifier: Modifier = Modifier
) {
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
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),

        ) {
        BookCarousel(sampleBooks)
    }

}

@Preview
@Composable
fun HomePageScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
//        HomePageScreen(
//            viewModel = remember { BookListViewModel() },
//            onBookClick = {},
//        )
    }
}


