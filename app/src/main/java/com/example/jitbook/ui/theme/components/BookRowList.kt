package com.example.jitbook.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.data.model.Book
import com.example.jitbook.ui.theme.JITBookTheme


@Composable
fun BookRowList(
    title: String,
    bookList: List<Book>,
    modifier: Modifier = Modifier
) {
    Box() {

    }
}

@Preview(showBackground = true)
@Composable
fun BookRowListPreview() {
}