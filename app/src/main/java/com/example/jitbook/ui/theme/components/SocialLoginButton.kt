package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionServices
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.R
import com.example.jitbook.ui.theme.JITBookTheme

@Composable
fun SocialLoginButton(
    servicesName: String,
    servicesIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary // Đặt màu nền tuỳ chỉnh, ví dụ: màu Facebook
        )
    ) {
        Icon(
            painter = servicesIcon,
            contentDescription = null,
            tint = Color.Unspecified,// Đảm bảo không có tint nào được áp dụng
            modifier = Modifier
                .padding(end = 12.dp)
        )
        Text(
            text = servicesName,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }

}

@Composable
fun SocialGoogleButton(
    servicesName: String,
    servicesIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkMode = isSystemInDarkTheme()

    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .then(
                if (!isDarkMode) Modifier.border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(25.dp)
                ) else Modifier
            )
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary // Đặt màu nền tuỳ chỉnh, ví dụ: màu Facebook
        )
    ) {
        Icon(
            painter = servicesIcon,
            contentDescription = null,
            tint = Color.Unspecified,// Đảm bảo không có tint nào được áp dụng
            modifier = Modifier
                .padding(end = 12.dp)
        )
        Text(
            text = servicesName,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleMedium
        )
    }

}
@Composable
fun SocialButton(
    servicesIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkMode = isSystemInDarkTheme()

    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .width(80.dp)
            .then(
                if (!isDarkMode) Modifier.border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(25.dp)
                ) else Modifier
            )
            .clip(RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        contentPadding = PaddingValues(0.dp) //  bỏ padding mặc định của Button
    ) {
        Icon(
            painter = servicesIcon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(24.dp) // bạn có thể chỉnh lại kích thước nếu cần
        )
    }
}

@Preview
@Composable
fun SocialLoginButtonPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SocialGoogleButton(
            servicesName = "Google",
            servicesIcon = painterResource(id = R.drawable.google),
            onClick = {}
        )
    }
}

@Preview
@Composable
fun SocialButtonPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SocialButton(
            servicesIcon = painterResource(id = R.drawable.google),
            onClick = {}
        )
    }
}