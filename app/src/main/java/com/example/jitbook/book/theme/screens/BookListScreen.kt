package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.R
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookList
import com.example.jitbook.book.theme.components.BookSearchBar
import com.example.jitbook.book.theme.components.FallingDots

@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultsListState = rememberLazyGridState()

    LaunchedEffect(state.searchResult) {
        searchResultsListState.animateScrollToItem(0)
    }
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
        Column {
            BookSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = {
                    onAction(BookListAction.OnSearchQueryChange(it))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )


                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        when {
                            state.errorMessage != null -> {
                                Text(
                                    text = state.errorMessage.asString(),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            state.searchQuery.isEmpty() -> {
                              Image(
                                  painter = painterResource(id = R.drawable.search),
                                  contentDescription = "Search empty",
                                    modifier = Modifier
                                        .size(300.dp),
                              )
                            }

                            else -> {
                                BookList(
                                    books = state.searchResult,
                                    onBookClick = {
                                        onAction(BookListAction.OnBookClick(it))
                                    },
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    scrollState = searchResultsListState
                                )
                            }
                        }

                    }
                }


        }
    }
}

private val books = (1..100).map {
    Book(
        id = "$it",
        title = "Book $it",
        imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        authors = listOf("Luc"),
        description = "Book $it description",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 5.3555,
        ratingsCount = 5,
        numPages = 100,
        numEdition = 3
    )
}

@Preview
@Composable
fun BookListScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        BookListScreen(

            state = BookListState(
                searchResult = books,
            ),
            onAction = {},
        )
    }
}