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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookCarousel
import com.example.jitbook.book.theme.components.FallingDots
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


