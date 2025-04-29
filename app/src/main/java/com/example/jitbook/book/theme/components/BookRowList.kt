package com.example.jitbook.book.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jitbook.book.data.model.Book


@Composable
fun BookRowList(
    title: String,
    bookList: List<com.example.jitbook.book.data.model.Book>,
    modifier: Modifier = Modifier
) {
    Box() {

    }
}

@Preview(showBackground = true)
@Composable
fun BookRowListPreview() {
}