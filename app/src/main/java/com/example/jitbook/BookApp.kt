package com.example.jitbook

import android.app.Activity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.data.network.KtorRemoteBookDataSource
import com.example.jitbook.book.data.repository.DefaultBookRepository
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BottomNavigationBar
import com.example.jitbook.book.theme.navigation.BookNavigationHost
import com.example.jitbook.book.theme.screens.BookListScreen
import com.example.jitbook.book.theme.screens.HomePageScreen
import com.example.jitbook.book.theme.viewmodel.AppThemeViewModel
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import com.example.jitbook.core.data.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(appThemeViewModel: AppThemeViewModel = viewModel()) {
    val isDarkMode by appThemeViewModel.isDarkMode
    val navController = rememberNavController()

    JITBookTheme(
        darkTheme = isDarkMode,
        dynamicColor = false
    ) {
        val layoutDirection = LocalLayoutDirection.current

        Surface(
            modifier = Modifier
                .padding(
                    start = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateStartPadding(layoutDirection),
                    end = WindowInsets.safeDrawing.asPaddingValues()
                        .calculateEndPadding(layoutDirection)
                )
        ) {
            BookNavigationHost(
                navController = navController,
                appThemeViewModel = appThemeViewModel
            )
        }
    }
}
