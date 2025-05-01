package com.example.jitbook

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.jitbook.book.data.network.KtorRemoteBookDataSource
import com.example.jitbook.book.data.repository.DefaultBookRepository
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.navigation.BookContentType
import com.example.jitbook.book.theme.navigation.BookNavigationType
import com.example.jitbook.book.theme.screens.BookListScreen
import com.example.jitbook.book.theme.screens.HomePageScreen
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import com.example.jitbook.core.data.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine

@Composable
fun App(
    engine: HttpClientEngine,
) {

    HomePageScreen(
        viewModel = remember {
            BookListViewModel(
                bookListRepository = DefaultBookRepository(
                    remoteBookDataSource = KtorRemoteBookDataSource(
                        httpClient = HttpClientFactory.create(engine)
                    )
                )
            )
        },
        onBookClick = {}
    )

}

@Composable
fun BookApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    var navigationType: BookNavigationType
    val contentType: BookContentType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = BookNavigationType.BOTTOM_NAVIGATION
            contentType = BookContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = BookNavigationType.NAVIGATION_RAIL
            contentType = BookContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = BookNavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = BookContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = BookNavigationType.BOTTOM_NAVIGATION
            contentType = BookContentType.LIST_ONLY
        }

    }
}