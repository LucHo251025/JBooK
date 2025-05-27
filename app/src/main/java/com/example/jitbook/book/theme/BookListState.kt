package com.example.jitbook.book.theme

import com.example.jitbook.book.data.model.Book
import com.example.jitbook.core.domain.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResult: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
)