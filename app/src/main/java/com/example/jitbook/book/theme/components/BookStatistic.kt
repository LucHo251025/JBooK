import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.theme.JITBookTheme

@Composable
fun ReadingStatsWithChart(
    booksRead: Int,
    readingGoal: Int,
    readingTimeMinutes: Int
) {
    val bookProgress = booksRead.toFloat() / readingGoal.coerceAtLeast(1)
    val timeProgress = readingTimeMinutes.toFloat() / 1440f // 1 ngày = 24 * 60 phút

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📊 Thống kê đọc sách",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Biểu đồ sách
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = bookProgress.coerceIn(0f, 1f),
                    modifier = Modifier.size(150.dp),
                    strokeWidth = 16.dp,
                    color = MaterialTheme.colorScheme.primary
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(bookProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "$booksRead / $readingGoal cuốn")
                }
            }

            // Biểu đồ thời gian đọc trong ngày (24h)
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = timeProgress.coerceIn(0f, 1f),
                    modifier = Modifier.size(150.dp),
                    strokeWidth = 16.dp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(timeProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${readingTimeMinutes / 60}h ${readingTimeMinutes % 60}m",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        StatCard(
            title = "Tổng thời gian đọc hôm nay",
            value = "${readingTimeMinutes / 60} giờ ${readingTimeMinutes % 60} phút",
            icon = Icons.Default.Timer
        )
    }
}


@Composable
fun StatCard(title: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewReadingStatsUI() {
    JITBookTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        ReadingStatsWithChart(
            booksRead = 8,
            readingGoal = 20,
            readingTimeMinutes = 120
        )
    }
}