package com.example.jitbook.book.theme.screens

import android.webkit.CookieManager
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
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.jitbook.book.theme.components.LoadingSpinner
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import com.example.jitbook.book.theme.viewmodel.BookDetailViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookDetailScreenRoot(
    authViewModel: AuthViewModel,
    viewModel: BookDetailViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    A(
        authViewModel= authViewModel,
        viewModel = viewModel,
        state = state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun A(
    authViewModel: AuthViewModel,
    viewModel: BookDetailViewModel,
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
) {
    state.book?.let { bookNonNull ->
        BookDetailScreen(
            viewModel = viewModel,
            currentUser = authViewModel.currentUser.value?.uid,
            state = state,
            isFavorite = state.isFavorite,
            onFavoriteClick = { onAction(BookDetailAction.OnFavoriteClick) },
            onBackClick = {
                onAction(BookDetailAction.OnBackClick)
                          },
            ratings = state.ratings,
            isLoadings = state.isLoading,
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
fun BookDetailScreen(
    currentUser: String?,
    viewModel: BookDetailViewModel,
    isFavorite: Boolean,
    state: BookDetailState,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    ratings: List<Rating>,
    isLoadings: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val book = state.book ?: return
    val scrollState = rememberScrollState()
    var isRead by remember { mutableStateOf(false) }
    val isRefreshing = state.isLoading
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)
    var ratingInput by remember { mutableStateOf(0) }
    var description by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    var editingReviewId by remember { mutableStateOf<String?>(null) }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.fetchRatings(viewModel.bookId) }, // Gọi lại hàm load dữ liệu
        modifier = modifier.fillMaxSize()
    ) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center

    ) {

        if (isRead) {
            when {
                state.book?.readableUrl.isNullOrBlank() && state.isLoading -> {
                    LoadingSpinner(modifier = Modifier.size(80.dp))
                }
                !state.book?.readableUrl.isNullOrBlank() -> {
                    BookReaderScreen(state.book!!.readableUrl!!)
                }
                else -> {
                    Text("Không thể tải nội dung sách", color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge)
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
                    averageRating = state.averageRating,
                    ratingsCount = state.numReviews,
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
                    rating = ratingInput,
                    description = description,
                    onRatingChanged = { ratingInput = it },
                    onDescriptionChanged = { description = it },
                    onSubmitClicked = {
                        if (isEditing && editingReviewId != null) {
                            viewModel.updateRating(editingReviewId!!, ratingInput, description)
                        } else {
                            viewModel.submitRating(ratingInput, description)
                        }

                        // Reset sau khi submit
                        ratingInput = 0
                        description = ""
                        isEditing = false
                        editingReviewId = null
                    },
                    onCancel = {
                        ratingInput = 0
                        description = ""
                        isEditing = false
                        editingReviewId = null
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
                    currentUser = currentUser?: "",
                    ratings = ratings,
                    onDeleteClicked = {
                        rating ->
                        viewModel.deleteRatingById(
                            rating.reviewId?:""
                        )
                        viewModel.fetchRatings(book.id)
                    },
                    onEditClicked = { rating ->
                        description = rating.comment?:""
                        ratingInput = rating.rating?:0
                        editingReviewId = rating.reviewId
                        isEditing = true

                    }

                )
            }
            }
        }
    }
}

@Composable
fun BookReaderScreen(url: String) {
    val context = LocalContext.current

    AndroidView(factory = { ctx ->
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        // Thiết lập các cookie đăng nhập cần thiết
        val domain = "https://archive.org"
        cookieManager.setCookie(domain, "logged-in-user=hothanhluc.251005%40gmail.com")
        cookieManager.setCookie(domain, "logged-in-sig=1778494649%201746958649%20H0xwOAbSCCEdrKfT5V22m%2BInmARFoaa3jCtyRSKmrJh24Ej7Q4k%2B%2BWnVW637X%2BiqWwWL8PYt7Ta50rPnSvzN9jr6Eirj7Re5QwUxj9rTMPDGKbBTQRnSXvfWkPl6se4jbp3pt7pi1O0e6lGvnkeuF5bPsqYWjHRENezM3pJ8gxMf95s%2BnXl7Q3EchF75o7erH3Tc1liodqAFZBDCjDRKkXEayevk2xo2Mhp6dDFoqp8ss3KyzAH8ZoWV5bcQjAZV44Qg6ZafU67XsUbharHdne4Y1DEtrx1UR3n6K8u8x6a0BXNYF%2BxyIR3IptPvF1e3KETSjNJy413TFB52HfKVPQ%3D%3D")
        cookieManager.setCookie(domain, "PHPSESSID=2ju72slptuhh8ni9tsv9m6ro47")

        // Gắn thêm ia-auth nếu cần thiết
        cookieManager.setCookie(domain, "ia-auth=520138ae709a946b388f3bb9d747ca9c")

        // Flush để đảm bảo cookie có hiệu lực
        cookieManager.flush()

        // Tạo và trả về WebView
        WebView(ctx).apply {
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
            authViewModel = koinViewModel<AuthViewModel>(),
        )
    }

}
