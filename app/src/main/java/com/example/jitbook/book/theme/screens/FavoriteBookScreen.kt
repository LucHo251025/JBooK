package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.components.BookList
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.LoadingSpinner
import com.example.jitbook.book.theme.viewmodel.FavoriteViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun FavoriteBooksScreen(
    viewModel: FavoriteViewModel,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing = uiState.isLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.fetchFavoriteBooks() }, // Gọi lại hàm load dữ liệu
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            FallingDots(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f) // nằm sau nội dung
            )
            when {
                uiState.isLoading -> {
                    LoadingSpinner(
                        modifier = Modifier
                            .size(80.dp)
                    )
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "Unknown error",
                        color = Color.Red,
                    )

                }

                uiState.favoriteBooks.isEmpty() -> {
                    Text(text = "No favorite books found")
                }

                else -> {
                    BookList(
                        uiState.favoriteBooks,
                        onBookClick = onBookClick,
                        modifier = Modifier
                            .zIndex(1f) // nằm trên FallingDots
                    )
                }
            }

        }
    }
}