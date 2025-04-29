package com.example.jitbook.book.theme.screens

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.InputDisplay
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.navigation.BookContentType


@Composable
fun ForgotPassWordScreen(
    modifier: Modifier = Modifier
) {

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

        ForgotPassWordContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp)
                .zIndex(1f), // nằm trên FallingDots
            onBackClick = { /* Handle back click */ },
            onSendClick = { /* Handle send click */ }
        )


    }
}

@Composable
fun ForgotPassWordContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSendClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(17.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween

    ) {
        Column {
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
                value = "",
                onValueChange = {},
                label = "Email",
                modifier = Modifier

            )
        }

        PrimaryButton(
            text = "Continue",
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(top = 40.dp, bottom = 20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        ForgotPassWordScreen(
        )
    }
}
