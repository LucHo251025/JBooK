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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.jitbook.book.theme.JITBookTheme
import com.example.jitbook.book.theme.components.BookCheckBox
import com.example.jitbook.book.theme.components.BookPasswordField
import com.example.jitbook.book.theme.components.FallingDots
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.viewmodel.AuthViewModel

@Composable
fun NewPassScreen(
    email: String,
    viewModel: AuthViewModel,
    onVerifyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


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
                            append("Create New Password")

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
                    text = "Enter your new password.If you forget it, then you have to do forgot password",
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Start
                )

                Spacer(
                    modifier = Modifier
                        .padding(15.dp)
                )

                BookPasswordField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
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
                    isChecked = false,
                    onCheckedChange = {}
                )


            }

            PrimaryButton(
                text = "Continue",
                onClick = {

                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun NewPassScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
    }
}

