package com.example.jitbook.book.theme.components

import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.book.theme.JITBookTheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 8,
    maxDotRadius: Float = 12f,
    minDotRadius: Float = 2f,
    spinnerRadius: Float = 32f,
    durationMillis: Int = 1500
) {
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing)
        ),
        label = "progress"
    )

    Canvas(modifier = modifier.size((spinnerRadius * 2).dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val angleStep = 2 * PI / dotCount
        val activeDot = (progress * dotCount).roundToInt() % dotCount

        for (i in 0 until dotCount) {
            // Tính khoảng cách theo thứ tự từ dot lớn nhất
            val distance = (i - activeDot + dotCount) % dotCount
            val scale = 1f - (distance.toFloat() / dotCount)
            val dotRadius = minDotRadius + (maxDotRadius - minDotRadius) * scale

            val angle = angleStep * i - PI / 2 // Bắt đầu từ trên cùng
            val x = center.x + spinnerRadius * cos(angle).toFloat()
            val y = center.y + spinnerRadius * sin(angle).toFloat()
            drawCircle(
                color = color,
                radius = dotRadius,
                center = Offset(x, y)
            )
        }
    }
}

@Preview
@Composable
fun LoadingSpinnerPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        LoadingSpinner(modifier = Modifier.size(80.dp))
    }
}