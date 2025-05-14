package com.example.jitbook.book.theme.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.jitbook.book.data.model.Book
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow

class SelectedBookViewModel: ViewModel() {
    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook

    fun onBookSelected(book: Book?) {
        _selectedBook.value = book
        book?.let { savePartialBookInfo(it) }
    }

    private fun savePartialBookInfo(book: Book) {
        val db = Firebase.firestore
        val cleanId = book.id.substringAfterLast("/")
        val bookData = hashMapOf(
            "id" to cleanId,
            "title" to book.title,
            "author" to book.authors,
            "coverUrl" to book.imageUrl
        )

        db.collection("books")
            .document(cleanId)
            .set(bookData)
            .addOnSuccessListener {
                Log.d("Firebase", "Partial book info saved successfully")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error saving book", e)
            }
    }
}