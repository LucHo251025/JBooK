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
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.R
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.BookCheckBox
import com.example.jitbook.ui.theme.components.BookPasswordField
import com.example.jitbook.ui.theme.components.FallingDots
import com.example.jitbook.ui.theme.components.InputDisplay
import com.example.jitbook.ui.theme.components.PrimaryButton
import com.example.jitbook.ui.theme.components.PrimaryTextFile
import com.example.jitbook.ui.theme.components.SocialButton
import com.example.jitbook.ui.theme.components.SocialGoogleButton
import com.example.jitbook.ui.theme.navigation.BookContentType
import com.example.jitbook.ui.theme.navigation.BookNavigationType

@Composable
fun LoginScreen(
    contentType: BookContentType,
//    loginViewModel: LoginViewModel = viewModel(),
//    onSuccessfulLogin: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )

        when(contentType){
            BookContentType.LIST_ONLY -> {
                LoginContent(modifier = modifier.padding(16.dp))
            }

            BookContentType.LIST_AND_DETAIL -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
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
                        LoginContent(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                        )
                    }
                }
            }

        }


    }


}

@Composable
fun LoginContent(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(17.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            buildAnnotatedString {
                append("Welcome there ")
                append("👋")
            },
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Please enter your email and password to continue",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(30.dp))


        InputDisplay(
            value = "",
            onValueChange = {},
            label = "Username / Email",
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))

        BookPasswordField(
            label = "Password",
            value = "",
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(15.dp))

        BookCheckBox(
            label = "Remember me",
            isChecked = false,
            onCheckedChange = {}
        )
        Spacer(modifier = Modifier.height(15.dp))
        Divider(
            color = Color.LightGray,
            thickness = 0.5.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    color = Color.LightGray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)

                )
                Text(
                    text = "or continue with",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                )
                Divider(
                    color = Color.LightGray,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
            ) {
                SocialButton(
                    servicesIcon = painterResource(id = R.drawable.google),
                    onClick = { /*TODO*/ },
                    modifier = Modifier

                )
                SocialButton(
                    servicesIcon = painterResource(id = R.drawable.facebook),
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                )

                SocialButton(
                    servicesIcon = painterResource(id = R.drawable.facebook),
                    onClick = { /*TODO*/ },
                    modifier = Modifier

                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            PrimaryButton(
                text = "Login",
                onClick = { /*TODO*/ },
                modifier = Modifier

            )
        }
    }
}

//
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    JITBookTheme(
//        darkTheme = false,
//        dynamicColor = false
//    ) {
//        LoginScreen(
//            contentType = BookContentType.LIST_ONLY
//        )
//    }
//}
//
//
//@Preview(
//    name = "Compact (Phone Portrait)",
//    showBackground = true,
//    widthDp = 360,
//    heightDp = 800
//)
//@Composable
//fun CompactLoginPreview() {
//    JITBookTheme(
//        darkTheme = false,
//        dynamicColor = false
//    ) {
//        LoginScreen(
//            contentType = BookContentType.LIST_ONLY
//        )
//    }
//}

@Preview(
    name = "Medium (Tablet Portrait)",
    showBackground = true,
    widthDp = 600,
    heightDp = 800
)
@Composable
fun MediumLoginPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        LoginScreen(
            contentType = BookContentType.LIST_ONLY
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
fun ExpandedLoginPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        LoginScreen(
            contentType = BookContentType.LIST_AND_DETAIL
        )
    }
}
