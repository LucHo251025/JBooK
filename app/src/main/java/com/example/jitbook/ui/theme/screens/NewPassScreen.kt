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
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.BookCheckBox
import com.example.jitbook.ui.theme.components.BookPasswordField
import com.example.jitbook.ui.theme.components.FallingDots
import com.example.jitbook.ui.theme.components.PrimaryButton
import com.example.jitbook.ui.theme.navigation.BookContentType

@Composable
fun NewPassScreen(
    contentType: BookContentType,
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

        when(contentType) {
            BookContentType.LIST_ONLY -> {
                NewPassContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(17.dp)
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
                        NewPassContent(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(17.dp)
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun NewPassContent(
    modifier: Modifier = Modifier
) {
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
                    style =MaterialTheme.typography.headlineLarge,
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
                value = "",
                onValueChange = {},
                label = "Password",
            )
            Spacer(
                modifier = Modifier
                    .padding(15.dp)
            )

            BookPasswordField(
                value = "",
                onValueChange = {},
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
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(bottom = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewPassScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        NewPassScreen(
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
fun CompactNewPassScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        NewPassScreen(
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
fun MediumNewPassScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        NewPassScreen(
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
fun ExpandedNewPassScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        NewPassScreen(
            contentType = BookContentType.LIST_AND_DETAIL
        )
    }
}