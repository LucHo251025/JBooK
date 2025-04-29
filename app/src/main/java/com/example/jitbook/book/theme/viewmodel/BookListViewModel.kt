package com.example.jitbook.book.theme.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.data.repository.DefaultBookRepository
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.book.theme.BookListAction
import com.example.jitbook.book.theme.BookListState
import com.example.jitbook.core.domain.DataError
import com.example.jitbook.core.domain.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel(
    private val bookListRepository: BookRepository
): ViewModel(){

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction){
        when(action) {
            is BookListAction.OnBookClick -> {

            }

            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }
}