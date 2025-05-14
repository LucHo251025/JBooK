package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.jitbook.R
import com.example.jitbook.book.theme.AuthState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.LoadingSpinner
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import okhttp3.Route

@Composable
fun SplashScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )

        Column(
            modifier = Modifier.
            fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "JITBook",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier
                    .height(150.dp)
            )
            Text(
                text = "Chào mừng bạn đến với JITBook",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
            )
            LoadingSpinner(
                modifier = Modifier
                    .size(80.dp)
            )
        }


    }
    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(Unit) {
        authViewModel.checkAuthState()
    }

    when (authState) {
        is AuthState.Authenticated -> {
            // Điều hướng đến trang chính
            LaunchedEffect(Unit) {
                navController.navigate(com.example.jitbook.book.app.Route.BookSubject.route) {
                    popUpTo(0)
                }
            }
        }
        is AuthState.Unauthenticated -> {
            // Điều hướng đến trang login
            LaunchedEffect(Unit) {
                navController.navigate(com.example.jitbook.book.app.Route.BookLogin.route) {
                    popUpTo(0)
                }
            }
        }
        else -> {
            // Hiển thị loading hoặc logo splash
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ){

    }
}