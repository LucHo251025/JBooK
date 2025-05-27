package com.example.jitbook.book.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import okhttp3.internal.http2.ErrorCode

// Màu theo Figma bạn đã định nghĩa
private val PrimaryColor = Color(0xFFFFA500) // Màu cam vàng
private val SecondaryColor = Color(0xFF333333) // Màu đen/xám đậm
private val ss = Color(0xFFFFCC80)


private val BackgroundColor = Color.White
private val OnPrimaryColor = Color.White
private val ErrorColor = Color(0xFFB00020) // Màu đỏ cho thông báo lỗi

// Light Mode
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    secondary = SecondaryColor,
    onSecondary = Color.Black,
    background = BackgroundColor,
    onBackground = Color.Black,
    surface = Color(0xFFF2F2F2),
    onSurface = ss,
    error = ErrorColor,
)

// Dark Mode
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor, //	Màu chính dùng cho button, icon, link, điểm nhấn chính
    onPrimary = SecondaryColor, // 	Màu chữ nằm trên primary, thường là trắng nếu primary là cam
    secondary = PrimaryColor,// 	Màu phụ dùng cho danh mục phụ, tab hoặc background phụ
    onSecondary = Color.White, // Màu chữ nằm trên màu phụ
    background = Color.Black, // Nền chính cho toàn app
    onBackground = Color.White, //Màu chữ hoặc icon trên nền
    surface = Color(0xFF1C1C1C), // Nền của card, hộp thoại, v.v
    onSurface = SecondaryColor ,//Màu chữ trên surface
    error = ErrorColor
)

@Composable
fun JITBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Nhớ có file Typography nếu cần
        content = content
    )
}
