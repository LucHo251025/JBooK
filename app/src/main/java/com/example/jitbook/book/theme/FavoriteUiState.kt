package com.example.jitbook.book.theme

import com.example.jitbook.book.data.model.Book

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoriteBooks: List<Book> = emptyList(),
    val favoriteBookIds: List<String> = emptyList()
)
