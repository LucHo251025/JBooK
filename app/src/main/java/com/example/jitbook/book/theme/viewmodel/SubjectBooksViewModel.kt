package com.example.jitbook.book.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.book.theme.SubjectBooksUiState
import com.example.jitbook.core.domain.onError
import com.example.jitbook.core.domain.onSuccess
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectBooksViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SubjectBooksUiState())
    val uiState: StateFlow<SubjectBooksUiState> = _uiState
    var fantasyBooks: List<Book>? = null

    private val subjects = listOf("romance", "fantasy", "history", "science_fiction")

    init {
        loadBooksBySubjects()
    }

    private fun loadBooksBySubjects() {
        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            val booksMap = mutableMapOf<String, List<Book>>()
            var errorMessage: String? = null

            val results = subjects.map { subject ->
                async {
                    subject to bookRepository.getBooksBySubject(subject)
                }
            }.awaitAll()

            results.forEach { (subject, result) ->
                result.onSuccess { books ->
                    booksMap[subject] = books
                    if (subject == "fantasy" || subject == "romance") {
                        fantasyBooks = books
                    }
                }
//                    .onError {
//                    errorMessage = "Failed to load $subject books."
//                }
            }

            val randomBooks = fantasyBooks?.shuffled()?.take(5).orEmpty()

            _uiState.value = SubjectBooksUiState(
                isLoading = false,
                booksBySubject = booksMap,
                randomBooks = randomBooks,
                error = null
            )
        }
    }

    fun loadRandomBooks(
        subject: String = "fantasy",
        count: Int = 5
    ) {
        viewModelScope.launch {
            val result = bookRepository.getBooksBySubject(subject)

            result.onSuccess { books ->
                val randomSelection = books.shuffled().take(count)
                _uiState.value = _uiState.value.copy(randomBooks = randomSelection)
            }.onError {
                _uiState.value = _uiState.value.copy(error = "Không thể lấy sách ngẫu nhiên từ $subject")
            }
        }
    }

}