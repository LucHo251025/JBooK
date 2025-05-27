package com.example.jitbook.book.theme

import com.example.jitbook.book.data.model.Book

data class SubjectBooksUiState(
    val isLoading: Boolean = false,
    val booksBySubject: Map<String,List<Book>> = emptyMap(),
    val error: String? = null,
    val randomBooks: List<Book> = emptyList(), // ✅ thêm dòng này

)
