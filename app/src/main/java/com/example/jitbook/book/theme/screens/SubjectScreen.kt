package com.example.jitbook.book.theme.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.book.theme.components.BookCarousel
import com.example.jitbook.book.theme.components.BookList
import com.example.jitbook.book.theme.components.BookListNew
import com.example.jitbook.book.theme.components.BookSection
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.LoadingSpinner
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
    val selectedSubject by viewModel.selectedSubject.collectAsState()

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
            if (selectedSubject != null) {
                item {
                    SubjectSelectedView(
                        selectedSubject = selectedSubject!!,
                        books = state.booksBySubject[selectedSubject] ?: emptyList(),
                        onClear = {
                            Log.d("GestureTest", "Swipe detected, clearing subject")
                            viewModel.clearSelectedSubject()
                        },
                        onBookClick = { book ->
                            selectedBookViewModel.onBookSelected(book)
                            val encodedId = URLEncoder.encode(book.id, StandardCharsets.UTF_8.toString())
                            navController.navigate(Route.BookDetail.createRoute(encodedId))
                        }
                    )
                }

        } else {
                item {
                    when {
                        state.isLoading -> {
                            LoadingSpinner(
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
                        onAllClick = {
                            viewModel.onSubjectSelected(subject)
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun SubjectSelectedView(
    selectedSubject: String,
    books: List<Book>,
    onClear: () -> Unit,
    onBookClick: (Book) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    if (dragAmount < -50f) { // giảm ngưỡng để dễ test hơn
                        onClear()
                    }
                }
            }
            .animateContentSize()
    ) {
        Text(
            text = selectedSubject.split('_')
                .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } },
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp)
        ) {
            BookListNew(
                books = books,
                onBookClick = onBookClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
