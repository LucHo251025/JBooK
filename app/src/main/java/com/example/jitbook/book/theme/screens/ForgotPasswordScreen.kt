package com.example.jitbook.book.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.R
import com.example.jitbook.book.theme.AuthState
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.InputDisplay
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ForgotPassWordScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    onEmailSent: (String) -> Unit

) {

    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val authState by viewModel.authState.observeAsState()


    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.PasswordResetEmailSent -> {
                // Handle success, for example, navigate or show a success message
                onEmailSent((authState as AuthState.PasswordResetEmailSent).email)
            }
            is AuthState.Error -> {
                // Show error message
                errorMessage = (authState as AuthState.Error).message
            }
            else -> {
                // Handle other states like Loading or Authenticated
                errorMessage = ""
            }
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
                .fillMaxSize()
                .padding(17.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,

                    ) {
                    Text(
                        buildAnnotatedString {
                            append("Forgot Password")

                        },
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,

                        )
                    Image(
                        painter = painterResource(id = R.drawable.icons8_key_188),
                        contentDescription = "Waving Hand"
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Enter your email address. We will send an OTP code for verification in the next step.",
                    color = MaterialTheme.colorScheme.onSecondary,
                )
                Spacer(modifier = Modifier.height(20.dp))
                InputDisplay(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = "Email",
                    modifier = Modifier

                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary
                )
            }else {
                PrimaryButton(
                    text = "Continue",
                    onClick = {
                        viewModel.sendPasswordReset(email)
                    }
                )

            }
        }
    }

}
