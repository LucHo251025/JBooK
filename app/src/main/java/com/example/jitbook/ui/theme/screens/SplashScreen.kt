package com.example.jitbook.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jitbook.R
import com.example.jitbook.ui.theme.JITBookTheme
import com.example.jitbook.ui.theme.components.FallingDots

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        FallingDots(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // nằm sau nội dung
        )

        Column(
            modifier = Modifier.
            fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "JITBook",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )

            Spacer(
                modifier = Modifier
                    .height(150.dp)
            )

            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.size(32.dp)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ){
        SplashScreen()
    }
}