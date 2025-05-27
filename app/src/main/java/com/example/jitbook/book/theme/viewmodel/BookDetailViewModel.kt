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

     val bookId: String = rawBookId
        .removePrefix("/works/")
        .removeSuffix(".json")

    private var readingStartTime: Long? = null

    init {
        viewModelScope.launch {
            launch { checkIfBookIsFavorite() }
            launch { fetchRatings(bookId) }
        }
    }

    fun startReadingTimer() {
        readingStartTime = System.currentTimeMillis()
        Log.d("BookDetailViewModel", "startReadingTimer: readingStartTime = $readingStartTime")

    }

    fun updateReadingTime() {

        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        val userId = user.uid

        val startTime = readingStartTime ?: run {
            Log.e("BookDetailViewModel", "updateReadingTime: readingStartTime is null")
            return
        }
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime // milliseconds

        val docId = "${userId}_$bookId"

        val readingTimeRef = db.collection("readingLogs").document(docId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(readingTimeRef)
            val existingTime = snapshot.getLong("duration") ?: 0L
            val newDuration = existingTime + duration

            transaction.set(readingTimeRef, mapOf(
                "userId" to userId,
                "bookId" to bookId,
                "duration" to newDuration,
                "lastRead" to FieldValue.serverTimestamp()
            ))

        }.addOnSuccessListener {
            Log.d("BookDetailViewModel", "userId = $userId, bookId = $bookId, duration = $duration")
            readingStartTime = null // reset timer
        }.addOnFailureListener {
            Log.e("BookDetailViewModel", "Failed to update reading time", it)
        }
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
        val docId = "${userId}_$bookId"

        db.collection("favorites").document(docId)
            .get()
            .addOnSuccessListener { document ->
                val isFav = document.exists()
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
        val docId = "${userId}_$bookId"

        val data = hashMapOf(
            "userId" to userId,
            "bookId" to bookId,
            "timestamp" to Timestamp.now()
        )

        db.collection("favorites").document(docId)
            .set(data)
            .addOnSuccessListener {
                checkIfBookIsFavorite()
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to add to favorites", e)
            }
    }

    fun removeBookFromFavorites(bookId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("favorites")
            .whereEqualTo("userId", userId)
            .whereEqualTo("bookId", bookId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    db.collection("favorites").document(document.id).delete()
                }
                checkIfBookIsFavorite()
            }
            .addOnFailureListener {
                Log.e("REMOVE_FAV", "Failed to remove: ${it.message}")
            }
    }


    private fun fetchBookReadableLink() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            bookRepository.getReadableLink(bookId)
                .onSuccess { readableUrl ->
                    Log.d("DEBUG", "readableUrl = $readableUrl")

                    _state.update {
                        it.copy(book = it.book?.copy(readableUrl = readableUrl), isLoading = false)
                    }
                }
        }
    }

    fun submitRating(rating: Int, comment: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        val defaultImageUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
        val userId = user.uid
        val userName = user.displayName ?: "Anonymous"

        val reviewData = hashMapOf(
            "book_id" to bookId,
            "user_id" to userId,
            "userName" to userName,
            "imageUrl" to defaultImageUrl,
            "content" to comment,
            "score" to rating,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("reviews")
            .add(reviewData)
            .addOnSuccessListener {
                Log.d("BookDetailViewModel", "Review submitted successfully.")
                fetchRatings(bookId)
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to submit review", e)
            }
    }


    fun fetchRatings(bookId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("reviews")
            .whereEqualTo("book_id", bookId)
            .get()
            .addOnSuccessListener { result ->
                Log.d("DEBUG", "Found ${result.size()} reviews for bookId = $bookId")

                val ratingList = result.documents.mapNotNull { doc ->
                    val reviewId = doc.id
                    val userId = doc.getString("user_id") ?: return@mapNotNull null
                    val rating = doc.getLong("score")?.toInt() ?: 0
                    val comment = doc.getString("content") ?: ""
                    val timestamp = doc.getTimestamp("timestamp")?.toDate()?.time ?: 0L
                    val sqlTimestamp = java.sql.Timestamp(timestamp)
                    val imageUrl = doc.getString("imageUrl") ?: ""
                    val userName = doc.getString("userName") ?: "Anonymous"

                    Rating(
                        reviewId = reviewId,
                        userId = userId,
                        rating = rating,
                        imageUrl = imageUrl,
                        comment = comment,
                        userName = userName,
                        timestamp = sqlTimestamp
                    )
                }

                val count = ratingList.size
                val averageRatingNew = ratingList.mapNotNull { it.rating }.average()
                val oldAverage = _state.value.book?.averageRating
                val oldCount = _state.value.book?.ratingsCount


                if (oldAverage != null && oldCount != null && count > 0) {
                    val newAverage =
                        (oldAverage * oldCount + averageRatingNew * count) / (oldCount + count)
                    val newCount = oldCount + count
                    _state.update {
                        it.copy(
                            ratings = ratingList,
                            averageRating = newAverage,
                            numReviews = newCount
                        )
                    }
                } else {
                    // Trường hợp lần đầu có đánh giá
                    _state.update {
                        it.copy(
                            ratings = ratingList,
                            averageRating = averageRatingNew,
                            numReviews = oldCount ?: 0 + count
                        )
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to fetch ratings", e)
            }
    }
    fun updateRating(
        reviewId: String,
        newRating: Int?,
        newComment: String?
    ) {
        if (reviewId.isBlank()) return

        val db = FirebaseFirestore.getInstance()

        val updatedFields = mutableMapOf<String, Any?>()
        newRating?.let { updatedFields["score"] = it }
        newComment?.let { updatedFields["content"] = it }
        updatedFields["timestamp"] = FieldValue.serverTimestamp()

        db.collection("reviews").document(reviewId)
            .update(updatedFields)
            .addOnSuccessListener {
                Log.d("BookDetailViewModel", "Rating updated successfully.")
                fetchRatings(bookId) // refresh ratings UI
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to update rating", e)
            }
    }


    fun deleteRatingById(reviewId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("reviews").document(reviewId)
            .delete()
            .addOnSuccessListener {
                Log.d("BookDetailViewModel", "Rating with ID $reviewId deleted successfully.")
                fetchRatings(bookId) // Cập nhật lại danh sách đánh giá
            }
            .addOnFailureListener { e ->
                Log.e("BookDetailViewModel", "Failed to delete rating with ID $reviewId", e)
            }
    }


}