package com.example.jitbook.book.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.jitbook.R
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.theme.AuthState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookCheckBox
import com.example.jitbook.book.theme.components.BookPasswordField
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.InputDisplay
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.components.SocialButton
import com.example.jitbook.book.theme.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(Route.BookList.route)
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }
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
                value = email,
                onValueChange = {
                    email = it
                },
                label = "Username / Email",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))

            BookPasswordField(
                label = "Password",
                value = password,
                onValueChange = {
                    password = it
                },
            )
            Spacer(modifier = Modifier.height(15.dp))

            BookCheckBox(
                label = "Remember me",
                isChecked = false,
                onCheckedChange = {

                }
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

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly
//                ) {
//                    SocialButton(
//                        servicesIcon = painterResource(id = R.drawable.google),
//                        onClick = { /*TODO*/ },
//                        modifier = Modifier
//
//                    )
//                    SocialButton(
//                        servicesIcon = painterResource(id = R.drawable.facebook),
//                        onClick = { /*TODO*/ },
//                        modifier = Modifier
//                    )
//
//                    SocialButton(
//                        servicesIcon = painterResource(id = R.drawable.facebook),
//                        onClick = { /*TODO*/ },
//                        modifier = Modifier
//
//                    )
//                }

                Spacer(modifier = Modifier.height(30.dp))

                PrimaryButton(
                    text = "Login",
                    onClick = {
                        authViewModel.login(email, password)
                    },
                    modifier = Modifier

                )
            }
        }


    }


}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        LoginScreen(
            navController = NavController(context = LocalContext.current),
            authViewModel = AuthViewModel()
        )
    }
}




