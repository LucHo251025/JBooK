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
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.FallingDots
import com.example.jitbook.ui.theme.components.OtpInputField
import com.example.jitbook.ui.theme.components.PrimaryButton
import com.example.jitbook.ui.theme.components.ResendCodeText
import com.example.jitbook.ui.theme.navigation.BookContentType
import kotlinx.coroutines.delay


@Composable
fun OTPScreen(
    contentType: BookContentType,
    modifier: Modifier = Modifier
) {

    val otpValue = remember { mutableStateOf("") }

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

        when (contentType) {
            BookContentType.LIST_ONLY -> {
                OTPContent(
                    otpValue = otpValue,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(17.dp),
                    contentType = BookContentType.LIST_ONLY
                )
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
                        OTPContent(
                            otpValue = otpValue,
                            modifier = Modifier
                                .fillMaxHeight(),
                            contentType = BookContentType.LIST_AND_DETAIL
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun OTPContent(
    contentType: BookContentType,
    otpValue: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var secondsLeft by remember { mutableStateOf(60) } // chặn resend trong 30s

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
                    contentType = contentType

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
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(top = 40.dp,bottom = 20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OTPScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OTPScreen(
            contentType = BookContentType.LIST_ONLY
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
fun CompactOTPScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OTPScreen(
            contentType = BookContentType.LIST_ONLY
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
fun MediumOTPScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OTPScreen(
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
fun ExpandedOTPScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OTPScreen(
            contentType = BookContentType.LIST_AND_DETAIL
        )
    }
}