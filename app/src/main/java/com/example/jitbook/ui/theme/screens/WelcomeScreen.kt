package com.example.jitbook.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.FallingDots
import com.example.jitbook.ui.theme.components.PrimaryButton
import com.example.jitbook.ui.theme.components.SecondaryButton
import com.example.jitbook.ui.theme.components.SocialGoogleButton
import com.example.jitbook.ui.theme.components.SocialLoginButton
import com.example.jitbook.ui.theme.navigation.BookContentType

@Composable
fun WelcomeScreen(
    contentType: BookContentType,
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

        when(contentType) {
            BookContentType.LIST_ONLY -> {
                WelcomeContent(
                    onLoginButtonClicked = onLoginButtonClicked,
                    onSignUpButtonClicked = onSignUpButtonClicked,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            BookContentType.LIST_AND_DETAIL -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(250.dp)
                                .padding(bottom = 16.dp)
                        )
                        Text(
                            text = "JITBook",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState()),
                        contentAlignment = Alignment.Center
                    ) {
//                        LoginContent(
//                            modifier = Modifier
//                                .fillMaxWidth(0.85f)
//                        )
                    }
                }

            }
        }

    }
}

@Composable
fun WelcomeContent(
    onLoginButtonClicked: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
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
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                text = "Get Started",
                onClick = { /*TODO*/ },
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
            contentType = BookContentType.LIST_ONLY,
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {}
        )
    }
}


@Preview(
    name = "Compact (Phone Portrait)",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun CompactWelcomeScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        WelcomeScreen(
            contentType = BookContentType.LIST_ONLY,
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {}
        )
    }
}

@Preview(
    name = "Medium (Tablet Portrait)",
    showBackground = true,
    widthDp = 600,
    heightDp = 800
)
@Composable
fun MediumWelcomeScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        WelcomeScreen(
            contentType = BookContentType.LIST_ONLY,
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {}
        )
    }
}


@Preview(
    name = "Expanded (Tablet Landscape)",
    showBackground = true,
    widthDp = 1000,
    heightDp = 800
)
@Composable
fun ExpandedWelcomeScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        WelcomeScreen(
            contentType = BookContentType.LIST_AND_DETAIL,
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {}
        )
    }
}