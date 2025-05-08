package com.example.jitbook.book.theme.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jitbook.book.app.Route
import com.example.jitbook.book.data.model.Rating
import com.example.jitbook.book.domain.BookRepository
import com.example.jitbook.book.theme.BookDetailAction
import com.example.jitbook.book.theme.BookDetailState
import com.example.jitbook.core.domain.onSuccess
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())

    val state = _state.asStateFlow()

    val rawBookId: String = savedStateHandle
        .get<String>("bookId")
        ?: throw IllegalArgumentException("Missing argument 'bookId'")

    // 2. Loại bỏ prefixx "/works/" và suffix ".json"
    private val bookId: String = rawBookId
        .removePrefix("/works/")
        .removeSuffix(".json")

    init {
        checkIfBookIsFavorite()
        fetchRatings()
    }

    fun onAction(action: BookDetailAction) {
        when (action) {

            is BookDetailAction.OnSelectedBookChange -> {
                _state.update { it.copy(book = action.book) }
                fetchBookDescription()
            }

            is BookDetailAction.OnFavoriteClick -> {
                toggleFavorite()
            }

            else -> Unit
        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    val currentBook = state.value.book

                    val updatedBook = currentBook?.copy(description = description)

                    _state.update {
                        it.copy(
                            book = updatedBook,
                            isLoading = false
                        )
                    }

                    if (updatedBook != null) {
                        fetchBookReadableLink()
                    }
                }

        }
    }

    private fun checkIfBookIsFavorite() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)
        userRef.get().addOnSuccessListener { document ->
            val favorites = document.get("favorites") as? List<String> ?: emptyList()
            val isFav = favorites.contains(bookId)
            _state.update { it.copy(isFavorite = isFav) }
        }
    }

    fun toggleFavorite() {
        val isCurrentlyFavorite = state.value.isFavorite
        if (isCurrentlyFavorite) {
            removeBookFromFavorites(bookId)
        } else {
            addBookToFavorites(bookId)
        }
        _state.update { it.copy(isFavorite = !isCurrentlyFavorite) }
    }

    fun addBookToFavorites(bookId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)
        userRef.update("favorites", FieldValue.arrayUnion(bookId))
            .addOnSuccessListener {
                checkIfBookIsFavorite()
            }
            .addOnFailureListener { /* Nếu chưa có field favorites, tạo mới */
                userRef.set(mapOf("favorites" to listOf(bookId)))
                    .addOnSuccessListener {
                        checkIfBookIsFavorite()
                    }
            }
    }

    fun removeBookFromFavorites(bookId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(userId)
        userRef.update("favorites", FieldValue.arrayRemove(bookId))
            .addOnSuccessListener {
                checkIfBookIsFavorite()
            }
    }



    private fun fetchBookReadableLink() {
        viewModelScope.launch {
            val result = bookRepository.getReadableLink(bookId)
            Log.d("DEBUG", "readableLink result: $result")

            bookRepository.getReadableLink(bookId)
                .onSuccess { readableUrl ->
                    Log.d("DEBUG", "readableUrl = $readableUrl")

                    _state.update { it.copy(book = it.book?.copy(readableUrl = readableUrl)) }
                }

        }
    }
    fun submitRating(rating: Int, comment: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        val defaultImageUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
        val imageUrl = user.photoUrl?.toString() ?: defaultImageUrl
        val userId = user.uid
        val userName = user.displayName ?: "Anonymous"

        val ratingData = hashMapOf(
            "userId" to userId,
            "userName" to userName,
            "imageUrl" to imageUrl,
            "rating" to rating,
            "comment" to comment,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("books")
            .document(bookId)
            .collection("ratings")
            .document(userId)
            .set(ratingData)
            .addOnSuccessListener {
                Log.d("BookDetailViewModel", "Rating submitted successfully.")
                fetchRatings()
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to submit rating", e)
            }
    }


    fun fetchRatings() {
        val db = FirebaseFirestore.getInstance()
        db.collection("books")
            .document(bookId)
            .collection("ratings")
            .get()
            .addOnSuccessListener { result ->
                val ratingList = result.documents.mapNotNull { doc ->
                    val userId = doc.getString("userId") ?: return@mapNotNull null
                    val rating = doc.getDouble("rating")?.toInt() ?: 0
                    val comment = doc.getString("comment") ?: ""
                    val imageUrl = doc.getString("imageUrl") ?: ""
                    val timestamp = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L
                    val sqlTimestamp = java.sql.Timestamp(timestamp)
                    val userName = doc.getString("userName") ?: "Anonymous"
                    Rating(
                        userId = userId,
                        rating = rating,
                        imageUrl = imageUrl,
                        comment = comment,
                        userName = userName,
                        timestamp = sqlTimestamp
                    )
                }

                _state.update {
                    it.copy(ratings = ratingList)
                }
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to fetch ratings", e)
            }
    }

}