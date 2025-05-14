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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
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
import com.example.jitbook.book.theme.components.OtpInputField
import com.example.jitbook.book.theme.components.PrimaryButton
import com.example.jitbook.book.theme.components.ResendCodeText
import com.example.jitbook.book.theme.viewmodel.AuthViewModel
import kotlinx.coroutines.delay


@Composable
fun OTPScreen(
    mail: String,
    viewModel: AuthViewModel,
    onVerifyClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val otpValue = remember { mutableStateOf("") }
    var secondsLeft by remember { mutableStateOf(60) } // chặn resend trong 30s

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
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
                            painter = painterResource(id = R.drawable.pin),
                            contentDescription = "Waving Hand",
                            modifier = Modifier
                                .size(60.dp)

                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(15.dp)
                    )

                    Text(
                        text = "We have sent the OTP verification code to your email address. Check your email and enter the code below.",
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(20.dp)
                    )

                    OtpInputField(
                        otp = otpValue,
                        count = 5,
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(20.dp)
                    )

                    LaunchedEffect(Unit) {
                        while (secondsLeft > 0) {
                            delay(1000)
                            secondsLeft--
                        }
                    }

                    if (secondsLeft > 0) {
                        Text(
                            text = "You can resend in ${secondsLeft}s",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        ResendCodeText(
                            onResendClick = {
                                secondsLeft = 60
                            },
                            isEnabled = true,
                        )
                    }
                }
            }


            PrimaryButton(
                text = "Continue",
                onClick = {

                },
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 20.dp)
            )
        }


    }

}





@Preview(showBackground = true)
@Composable
fun OTPScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
//        OTPScreen()
    }
}

