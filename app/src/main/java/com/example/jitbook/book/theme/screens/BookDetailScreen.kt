package com.example.jitbook.book.theme.screens

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.model.Rating
import com.example.jitbook.book.theme.BookDetailAction
import com.example.jitbook.book.theme.BookDetailState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookDescription
import com.example.jitbook.book.theme.components.BookHeaderSection
import com.example.jitbook.book.theme.components.BookInfo
import com.example.jitbook.book.theme.components.BookListRating
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.InputRating
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.viewmodel.BookDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    A(
        viewModel = viewModel,
        state = state,
        onAction = { action ->
            when (action) {
                is BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun A(
    viewModel: BookDetailViewModel,
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    state.book?.let { bookNonNull ->
        BookDetailScreen(
            viewModel = viewModel,
            isFavorite = state.isFavorite,
            onFavoriteClick = { onAction(BookDetailAction.OnFavoriteClick) },
            onBackClick = { onAction(BookDetailAction.OnBackClick) },
            book = bookNonNull,
            ratings = state.ratings,
            isLoadings = state.isLoading,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    ratings: List<Rating>,
    isLoadings: Boolean = false,
    book: Book,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    var isRead by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        if (isRead) {
            book?.readableUrl?.takeIf { it.isNotBlank() }?.let { url ->
                BookReaderScreen(url)
            } ?: run {
                if (isLoadings) {
                    CircularProgressIndicator()
                }
            }

        } else {
            
            FallingDots(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f) // nằm sau nội dung
            )
            Column(
                modifier = modifier
                    .verticalScroll(scrollState) // Thêm modifier này
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                BookHeaderSection(
                    title = book.title,
                    imageUrl = book.imageUrl,
                    authors = book.authors,
                    firstPublishYear = book.firstPublishYear,
                    isFavorite = isFavorite,
                    onFavoriteClick = onFavoriteClick,
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
                    onClick = {
                        isRead = !isRead
                    },
                    modifier = modifier
                        .fillMaxSize()
                        .height(55.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))

                BookDescription(
                    isLoading = isLoadings,
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
                InputRating(
                    onSubmitClicked = { rating, description ->
                        viewModel.submitRating(rating, description)
                    }
                )
                Spacer(modifier = Modifier.height(19.dp))

                Text(
                    text = "Ratings & Reviews",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(19.dp))

                BookListRating(
                    ratings = ratings,
                    onRatingClicked = {
                    },
                )
            }
        }
    }
}

@Composable
fun BookReaderScreen(url: String) {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }, modifier = Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun BookDetailScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false,
    ) {
        val viewModel = koinViewModel<BookDetailViewModel>()

        BookDetailScreenRoot(
            viewModel = viewModel,
            onBackClick = { /* Handle back click */ },
        )
    }

}
