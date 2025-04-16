package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .height(56.dp),

        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }

}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .height(56.dp),

        ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
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