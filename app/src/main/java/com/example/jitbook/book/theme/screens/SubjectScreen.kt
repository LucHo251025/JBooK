package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.data.dto.SubjectBookDto
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.book.theme.components.BookCarousel
import com.example.jitbook.book.theme.components.BookSection
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.viewmodel.SelectedBookViewModel
import com.example.jitbook.book.theme.viewmodel.SubjectBooksViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SubjectScreen(
    selectedBookViewModel: SelectedBookViewModel,
    navController: NavController,
    viewModel: SubjectBooksViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f), // đảm bảo nằm trên FallingDots
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp),
                        )
                    }

                    state.error != null -> {
                        Text("Error: ${state.error}", modifier = Modifier.padding(16.dp))
                    }
                }
            }

            item {
                BookCarousel(
                    books = state.randomBooks,
                    onBookClick = { book ->
                        selectedBookViewModel.onBookSelected(book)
                        val encodedId =
                            URLEncoder.encode(book.id, StandardCharsets.UTF_8.toString())
                        navController.navigate(Route.BookDetail.createRoute(encodedId))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

            items(state.booksBySubject.toList()) { (subject, books) ->
                BookSection(
                    title = subject.split('_')
                        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } },
                    books = books,
                    onBookClick = { book ->
                        selectedBookViewModel.onBookSelected(book)
                        val encodedId =
                            URLEncoder.encode(book.id, StandardCharsets.UTF_8.toString())
                        navController.navigate(Route.BookDetail.createRoute(encodedId))
                    },
                    onAllClick = { /* TODO */ }
                )
            }
        }
    }

}