package com.example.jitbook.book.theme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jitbook.book.data.model.Book
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.book.theme.FavoriteUiState
import com.example.jitbook.core.domain.onError
import com.example.jitbook.core.domain.onSuccess
import com.example.jitbook.core.presentation.toUiText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class FavoriteViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState

    init {
        fetchFavoriteBooks()
    }


    fun fetchFavoriteBooks() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: run {
            _uiState.value = _uiState.value.copy(error = "User not logged in")
            return
        }
        Log.d("FAV_DEBUG", "Current User UID: $userId")

        db.collection("favorites")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val favoriteIds = result.documents.mapNotNull { it.getString("bookId") }
                _uiState.value = _uiState.value.copy(favoriteBookIds = favoriteIds)

                if (favoriteIds.isNotEmpty()) {
                    fetchBookDetails(favoriteIds)
                } else {
                    _uiState.value = _uiState.value.copy(favoriteBooks = emptyList())
                }
            }
            .addOnFailureListener {
                _uiState.value = _uiState.value.copy(error = "Failed to fetch favorites: ${it.message}")
            }
    }

    private fun fetchBookDetails(ids: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            bookRepository.getFavoriteBooks(ids)
                .onSuccess { books ->

                    _uiState.value = _uiState.value.copy(
                        favoriteBooks = books,
                        isLoading = false
                    )

                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }
        }
    }
}
