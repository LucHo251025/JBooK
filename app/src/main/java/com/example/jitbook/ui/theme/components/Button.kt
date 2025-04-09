package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.ui.theme.AppTypography
import com.example.jitbook.ui.theme.JITBookTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary) ,
        modifier = modifier
            .fillMaxWidth()


    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

}

@Preview
@Composable
fun PrimaryButtonPreview() {
    JITBookTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        PrimaryButton(
            text = "Continue",
            onClick = {}
        )
    }
}