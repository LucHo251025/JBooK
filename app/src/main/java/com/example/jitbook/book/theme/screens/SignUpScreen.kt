package com.example.jitbook.book.theme.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jitbook.R
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.AuthState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookCheckBox
import com.example.jitbook.book.theme.components.BookPasswordField
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.InputDisplay
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import io.ktor.util.valuesOf

@Composable
fun SignUpScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()

    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(Route.BookLogin.route)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_LONG
            ).show()

            else -> Unit
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )
        Column(
            modifier = modifier
                .padding(17.dp)
                .fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            horizontalAlignment = androidx.compose.ui.Alignment.Start
        ) {
            Column {
                Row(
                    modifier = Modifier,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,

                    ) {
                    Text(
                        buildAnnotatedString {
                            append("Create Account")

                        },
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                        )
                    Image(
                        painter = painterResource(id = R.drawable.reset_password),
                        contentDescription = "Waving Hand",
                        modifier = Modifier
                            .size(60.dp)

                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(10.dp)
                )
                Text(
                    text = "Enter your email and password to continue",
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Start
                )

                InputDisplay(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = "Email",
                )

                Spacer(
                    modifier = Modifier
                        .padding(15.dp)
                )

                BookPasswordField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = "Password",
                )
                Spacer(
                    modifier = Modifier
                        .padding(15.dp)
                )

                BookPasswordField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                    },
                    label = "Confirm Password",
                )

                Spacer(
                    modifier = Modifier
                        .padding(13.dp)
                )

                BookCheckBox(
                    label = "Remember me",
                    isChecked = rememberMe,
                    onCheckedChange = {
                        rememberMe = it
                    }
                )


            }

            PrimaryButton(
                text = "Continue",
                onClick = {
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.signup(email, password)
                    }
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpRreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        val navController :NavController = rememberNavController()
        SignUpScreen(
            navController =navController,
            authViewModel = AuthViewModel()
        )
    }
}

