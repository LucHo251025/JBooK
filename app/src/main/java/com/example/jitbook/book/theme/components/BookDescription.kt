package com.example.jitbook.book.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.theme.JITBookTheme

@Composable
fun BookDescription(
    description: String?,
) {

    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        BookTitleWithAction(
            title = "Description",
            action = { /* TODO: Implement action */ }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description?:"No description available",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondary,
            maxLines = if(isExpanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable {
                isExpanded = !isExpanded
            }
        )
    }

}

@Preview
@Composable
fun BookDescriptionPreview() {
    JITBookTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        BookDescription(
            description = "This is a sample description of the book. It contains information about the book's content, themes, and other relevant details that may interest potential readers. The description should be concise and engaging to attract readers' attention."
        )
    }
}