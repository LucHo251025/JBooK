package com.example.jitbook.book.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jitbook.book.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedBookViewModel: ViewModel() {
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook

    fun onBookSelected(book: Book?) {
        _selectedBook.value = book
    }
}