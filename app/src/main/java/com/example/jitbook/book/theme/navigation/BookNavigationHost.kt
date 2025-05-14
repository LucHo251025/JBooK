package com.example.jitbook.book.theme.navigation

import android.net.Uri
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
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
import com.example.jitbook.R
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.BookDetailAction
import com.example.jitbook.book.theme.components.BookTopBar
import com.example.jitbook.book.theme.components.BottomNavigationBar
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.screens.BookDetailScreenRoot
import com.example.jitbook.book.theme.screens.FavoriteBooksScreen
import com.example.jitbook.book.theme.screens.ForgotPassWordScreen
import com.example.jitbook.book.theme.screens.HomePageScreen
import com.example.jitbook.book.theme.screens.LoginScreen
import com.example.jitbook.book.theme.screens.NewPassScreen
import com.example.jitbook.book.theme.screens.OTPScreen
import com.example.jitbook.book.theme.screens.SignUpScreen
import com.example.jitbook.book.theme.screens.SplashScreen
import com.example.jitbook.book.theme.screens.SubjectScreen
import com.example.jitbook.book.theme.screens.WelcomeScreen
import com.example.jitbook.book.theme.viewmodel.AppThemeViewModel
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import com.example.jitbook.book.theme.viewmodel.BookDetailViewModel
import com.example.jitbook.book.theme.viewmodel.BookListViewModel
import com.example.jitbook.book.theme.viewmodel.FavoriteViewModel
import com.example.jitbook.book.theme.viewmodel.SelectedBookViewModel
import com.example.jitbook.book.theme.viewmodel.SubjectBooksViewModel
import com.example.jitbook.screen.ProfileScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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

    val subjectViewModel = koinViewModel<SubjectBooksViewModel>()
    val currentDestination by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            BookTopBar(
                viewModel = subjectViewModel,
                currentRoute = currentDestination?.destination?.route,
                onBackClick = { navController.navigateUp() },
                modifier = Modifier
                    .zIndex(1f)

            )
        },
        bottomBar = {
            if (currentDestination?.destination?.route in bottomBarRoutes) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Route.BookGraph.route,
            modifier = Modifier
                .padding(innerPadding)

        ) {
            navigation(
                startDestination = Route.BookWelcome.route,
                route = Route.BookGraph.route
            ) {
                composable(Route.BookResetPassword.route){
                    val viewModel = koinViewModel<AuthViewModel>()
                    ForgotPassWordScreen(
                        viewModel = viewModel,
                        onEmailSent = {
                            navController.navigate(Route.BookLogin.route)
                        },
                        modifier = modifier
                    )
                }
                composable(Route.BookWelcome.route) {
                    val viewModel = koinViewModel<AuthViewModel>()
                    val authState by viewModel.authState.observeAsState()
                    val context = LocalContext.current
                    val webClientId = stringResource(R.string.default_web_client_id)
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(webClientId)
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    // Gọi kiểm tra trạng thái khi mở Composable
                    LaunchedEffect(Unit) {
                        viewModel.checkAuthState()
                    }
                    // Tạo launcher
                    val launcher =
                        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                            try {
                                val account = task.getResult(ApiException::class.java)
                                account.idToken?.let { idToken ->
                                    viewModel.signInWithGoogle(idToken)
                                }
                            } catch (e: ApiException) {
                                Log.e("GoogleSignIn", "Google sign in failed", e)
                            }
                        }
                    LaunchedEffect(authState) {
                        if (authState == com.example.jitbook.book.theme.AuthState.Authenticated) {
                            navController.navigate(Route.BookSubject.route) {
                                popUpTo(Route.BookWelcome.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    WelcomeScreen(
                        onLoginGoogleButtonClicked = {
                            val intent = googleSignInClient.signInIntent
                            launcher.launch(intent)

                        },
                        onLoginButtonClicked = {
                            navController.navigate(Route.BookLogin.route)
                        },
                        onSignUpButtonClicked = {
                            navController.navigate(Route.BookSignUp.route)
                        },
                        modifier = modifier
                    )
                }
                composable(Route.BookFavorite.route) {
                    val viewModel = koinViewModel<FavoriteViewModel>()
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)



                    LaunchedEffect(true) {
                        viewModel.fetchFavoriteBooks()
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
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedBookViewModel.onBookSelected(null)
                    }
                    SubjectScreen(
                        selectedBookViewModel = selectedBookViewModel,
                        navController = navController,
                        viewModel = subjectViewModel,
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
                    val authViewModel = koinViewModel<AuthViewModel>()
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it))
                        }

                    }

                    BookDetailScreenRoot(
                        authViewModel = authViewModel,
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

