package com.example.jitbook.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.jitbook.R
import com.example.jitbook.data.model.Book
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.BookCard
import com.example.jitbook.ui.theme.components.BookCarousel
import com.example.jitbook.ui.theme.components.BookList
import com.example.jitbook.ui.theme.components.BottomNavigationBar
import com.example.jitbook.ui.theme.components.OtpInputField
import com.example.jitbook.ui.theme.components.PassWord
import com.example.jitbook.ui.theme.components.PrimaryTextFile
import com.example.jitbook.ui.theme.components.SocialLoginButton
import com.example.jitbook.ui.theme.components.pxToDp

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {

//    val navController = rememberNavController()

//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(navController)
//        }
//    ) { innerPadding ->
        // Mock content
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//            contentAlignment = Alignment.Center
//        ) {
//            var password by remember { mutableStateOf("") }
//            Column(
//                modifier = modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                PrimaryTextFile(
//                    label = "Enter your name",
//                    value = "",
//                    onValueChange = {},
//                    modifier = modifier
//                        .fillMaxWidth()
//                )
//                PassWord(
//                    label = "Enter your password",
//                    value = password,
//                    onValueChange = {
//                        password = it
//                    },
//                    modifier = modifier
//                        .fillMaxWidth()
//                )
//
//                val otpValue = remember { mutableStateOf("") }
//
//                OtpInputField(
//                    otp = otpValue,
//                    count = 5,
//                    otpBoxModifier = Modifier
//                        .border(
//                            3.pxToDp(),
//                            MaterialTheme.colorScheme.primary,
//                            shape = RoundedCornerShape(12.pxToDp())
//                        ),
//                    otpTextType = KeyboardType.Number,
//                    textColor = MaterialTheme.colorScheme.onSecondary
//                )
//
//                SocialLoginButton(
//                    servicesName = "Facebook",
//                    servicesIcon = painterResource(id = R.drawable.facebook),
//                    onClick = {},
//                    modifier = modifier
//                        .fillMaxWidth()
//                )
//            }

        val sampleBooks = listOf(
            Book(
                title = "Avengers - Infinity War",
                imageUrl = "https://image.tmdb.org/t/p/w500/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
                duration = "2h29m",
                genres = "Action, Adventure, Sci-fi",
                rating = 4.8,
                ratingCount = 1222
            ),

            Book(
                title = "The Dark Knight",
                imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                duration = "2h32m",
                genres = "Action, Crime, Drama",
                rating = 4.9,
                ratingCount = 1520
            ),
            Book(
                title = "The Dark Knight",
                imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                duration = "2h32m",
                genres = "Action, Crime, Drama",
                rating = 4.9,
                ratingCount = 1520
            ),
            Book(
                title = "The Dark Knight",
                imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                duration = "2h32m",
                genres = "Action, Crime, Drama",
                rating = 4.9,
                ratingCount = 1520
            ),
            Book(
                title = "The Dark Knight",
                imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                duration = "2h32m",
                genres = "Action, Crime, Drama",
                rating = 4.9,
                ratingCount = 1520
            )
        )

        BookCarousel(
            sampleBooks
        )


//    BookList(
//        books = sampleBooks,
//        onBookClick = { book ->
//            // Handle book click
//        },
//        modifier = Modifier
//    )

//    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    JITBookTheme(
        darkTheme = true, dynamicColor = false
    ) {
        LoginScreen(
            modifier = Modifier
        )
    }
}
