package com.example.jitbook.book.theme.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun ResendCodeText(
    onResendClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val normalText = "Didn't receive the code? "
    val resendText = "Resend"

    val annotatedText = buildAnnotatedString {
        append(normalText)
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        ) {
            pushStringAnnotation(tag = "RESEND", annotation = "resend_tag")
            append(resendText)
            pop()
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "RESEND", start = offset, end = offset)
                .firstOrNull()?.let {
                    if (isEnabled) onResendClick()
                }
        },
    )
}
