package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.R
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.components.SecondaryButton
import com.example.jitbook.book.theme.components.SocialGoogleButton

@Composable
fun WelcomeScreen(
    onLoginGoogleButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )

        WelcomeContent(
            onLoginButtonClicked = onLoginButtonClicked,
            onSignUpButtonClicked = onSignUpButtonClicked,
            onLoginGoogleButtonClicked =  onLoginGoogleButtonClicked,
            modifier = Modifier
                .fillMaxSize()
        )


    }

}


@Composable
fun WelcomeContent(
    onLoginGoogleButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Column(
        modifier = modifier.fillMaxSize(),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 3) // Chiếm nửa chiều dọc
        ) {


            Image(
                painter = painterResource(id = R.drawable.__light_welcome_screen),
                contentDescription = "Welcome Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )


            // Lớp mờ dưới đáy ảnh
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 1f)
                            )
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    append("Welcome to ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("JITbook ")
                    }
                    append("👋")
                },
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "The Number One Best Ebook Store & Reader\nApplication in this Century",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(30.dp))

            SocialGoogleButton(
                servicesName = "Continue with Google",
                servicesIcon = painterResource(id = R.drawable.google),
                onClick = { onLoginGoogleButtonClicked() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "Get Started",
                onClick = { onSignUpButtonClicked() },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                text = "I Already have an account",
                onClick = onLoginButtonClicked,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        WelcomeScreen(
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {},
            onLoginGoogleButtonClicked = {},
        )
    }
}
