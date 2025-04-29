package com.example.jitbook.book.theme.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.jitbook.book.theme.JITBookTheme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun BookAlert(
    icon: ImageVector,
    title: String,
    message: String,
    isLoading: Boolean = false,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .height(400.dp)
                .width(300.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            // Dùng Box để chồng layer: hiệu ứng dưới, nội dung trên
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // 🎉 Hiệu ứng chấm rơi phủ toàn bộ card
                FallingDots(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(0f) // nằm sau nội dung
                )

                // Nội dung hiển thị
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .zIndex(1f), // nằm trên hiệu ứng
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Icon với nền tròn
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    if (isLoading) {
                        Spacer(modifier = Modifier.height(24.dp))
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun FallingDots(
    modifier: Modifier = Modifier,
    dotCount: Int = 30
) {
    val dots = remember {
        List(dotCount) {
            FallingDot(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 15f + 10f,
                speed = Random.nextFloat() * 2f + 1f
            )
        }
    }

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "falling")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10_000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        dots.forEach { dot ->
            val newY = (dot.y * height + dot.speed * time * height) % height
            val starPath = createStarPath(Offset(dot.x * width, newY), dot.radius)
            drawPath(
                path = starPath,
                color = Color(0xFFFFA500).copy(alpha = 0.3f)
            )

        }
    }
}

fun createStarPath(center: Offset, radius: Float, points: Int = 5): Path {
    val path = Path()
    val angle = (2.0 * Math.PI / points).toFloat()

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else radius / 2
        val x = (center.x + r * kotlin.math.cos(i * angle / 2)).toFloat()
        val y = (center.y + r * kotlin.math.sin(i * angle / 2)).toFloat()

        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()
    return path
}


data class FallingDot(
    val x: Float,
    val y: Float,
    val radius: Float,
    val speed: Float
)



@Preview(showBackground = true)
@Composable
fun BookAlertPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        BookAlert(
            icon = Icons.Default.CheckCircle,
            title = "Success!",
            message = "Your operation completed successfully.",
            isLoading = true,
            onDismiss = { /* close dialog */ }
        )
    }

}