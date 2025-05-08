package com.example.jitbook.book.theme.navigation

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.data.dto.SubjectBookDto
import com.example.jitbook.book.theme.BookDetailAction
import com.example.jitbook.book.theme.components.BookTopBar
import com.example.jitbook.book.theme.components.BottomNavigationBar
import com.example.jitbook.book.theme.screens.BookDetailScreen
import com.example.jitbook.book.theme.screens.BookDetailScreenRoot
import com.example.jitbook.book.theme.screens.FavoriteBooksScreen
import com.example.jitbook.book.theme.screens.HomePageScreen
import com.example.jitbook.book.theme.screens.LoginScreen
import com.example.jitbook.book.theme.screens.SignUpScreen
import com.example.jitbook.book.theme.screens.SplashScreen
import com.example.jitbook.book.theme.screens.SubjectScreen
import com.example.jitbook.book.theme.viewmodel.AppThemeViewModel
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import com.example.jitbook.book.theme.viewmodel.BookDetailViewModel
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import com.example.jitbook.book.theme.viewmodel.FavoriteViewModel
import com.example.jitbook.book.theme.viewmodel.SelectedBookViewModel
import com.example.jitbook.book.theme.viewmodel.SubjectBooksViewModel
import com.example.jitbook.screen.ProfileScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookNavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appThemeViewModel: AppThemeViewModel

) {
    val bottomBarRoutes = listOf(
        Route.BookList.route,
        Route.BookDetail.route,
        Route.BookSubject.route,
        Route.BookFavorite.route,
        Route.BookProfile.route,
    )

    val currentDestination by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            BookTopBar(
                currentRoute = currentDestination?.destination?.route,
                onBackClick = { navController.navigateUp() },
                onSettingsClick = { /* TODO */ },
                onSearchClick = { /* TODO */ },
                onNotificationClick = {
                    navController.navigate(Route.BookSubject.route) // ví dụ chuyển đến màn hình Notification
                }
            )
        },
        bottomBar = {
            if (currentDestination?.destination?.route in bottomBarRoutes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.BookGraph.route,
            modifier = Modifier
                .padding(innerPadding)

            ) {
            navigation(
                startDestination = Route.BookSplash.route,
                route = Route.BookGraph.route
            ) {
                composable(Route.BookFavorite.route) {
                    val viewModel = koinViewModel<FavoriteViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onBookSelected(null)
                    }

                    FavoriteBooksScreen(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onBookSelected(book)
                            navController.navigate(Route.BookDetail.createRoute(Uri.encode(book.id)))
                        },
                        modifier = modifier
                    )
                }
                composable(Route.BookSplash.route) {
                    val viewModel = koinViewModel<AuthViewModel>()
                    SplashScreen(
                        navController = navController,
                        authViewModel = viewModel,
                        modifier = modifier
                    )
                }

                composable(Route.BookProfile.route) {
                    val viewModel = koinViewModel<AuthViewModel>()
                    ProfileScreen(
                        navController = navController,
                        authViewModel = viewModel,
                        appThemeViewModel = appThemeViewModel

                    )
                }
                composable(Route.BookSubject.route) {
                    val viewModel = koinViewModel<SubjectBooksViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onBookSelected(null)
                    }
                    SubjectScreen(
                        selectedBookViewModel = selectedBookViewModel,
                        navController = navController,
                        viewModel = viewModel,
                        modifier = modifier
                    )
                }

                composable(Route.BookSignUp.route) {
                    val viewModel = koinViewModel<AuthViewModel>()

                    SignUpScreen(
                        navController = navController,
                        authViewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable(Route.BookLogin.route) {
                    val viewModel = koinViewModel<AuthViewModel>()
                    LoginScreen(
                        navController = navController,
                        authViewModel = viewModel,
                        modifier = modifier
                    )
                }
                composable(Route.BookList.route) {
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onBookSelected(null)
                    }
                    HomePageScreen(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onBookSelected(book)
                            navController.navigate(Route.BookDetail.createRoute(Uri.encode(book.id)))
                        },
                        modifier = modifier
                    )
                }

                composable(
                    route = Route.BookDetail.route,
                    arguments = listOf(navArgument("bookId") { type = NavType.StringType })
                ) {
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val viewModel = koinViewModel<BookDetailViewModel>()
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                        }

                    }

                    BookDetailScreenRoot(
                        viewModel = viewModel,

                        onBackClick = {
                            navController.navigateUp()
                        }
                    )

                }
            }
        }
    }

}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavHostController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}

