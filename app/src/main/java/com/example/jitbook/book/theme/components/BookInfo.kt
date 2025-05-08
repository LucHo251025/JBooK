package com.example.jitbook.book.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jitbook.book.theme.JITBookTheme

@Composable
fun BookInfo(
    averageRating: Double?,
    ratingsCount: Int?,
    numPages: Int?,
    numEdition: Int?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val roundedAverage = if (averageRating != null) String.format("%.1f", averageRating) else "N/A"

        InfoItem(
            title = roundedAverage,
            subtitle = ratingsCount?.toString() + " reviews" ?: "N/A",
            icon = Icons.Default.Star,
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(35.dp)
                .background(Color(0xFFD9CFCF))   // Màu sắc đường kẻ
        )
        InfoItem(
            title = numPages?.toString() ?: "N/A",
            subtitle = "Pages",
            icon = Icons.Default.MenuBook,
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(35.dp)
                .background(Color(0xFFD9CFCF))   // Màu sắc đường kẻ
        )
        InfoItem(
            title = numEdition?.toString() ?: "N/A",
            subtitle = "Number of Editions",
            icon = Icons.Default.Description,
        )
    }
}

@Composable
private fun InfoItem(
    title: String,
    subtitle: String,
    icon: ImageVector
) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = subtitle,
            fontSize = 8.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookInfoPreview() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        BookInfo(
            averageRating = 4.5,
            ratingsCount = 1000,
            numPages = 300,
            numEdition = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }

}