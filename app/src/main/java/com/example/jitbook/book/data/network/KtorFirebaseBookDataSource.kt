//package com.example.jitbook.book.data.network
//
//import com.example.jitbook.book.data.model.Rating
//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth
//import com.google.firebase.firestore.FieldValue
//import com.google.firebase.firestore.firestore
//import kotlinx.coroutines.tasks.await
//
//class KtorFirebaseBookDataSource() : FirebaseBookDataSource {
//    private val firestore = Firebase.firestore
//    private val auth = Firebase.auth
//
//    override suspend fun getRatings(bookId: String): List<Rating> {
//        val snapshot = firestore.collection("books")
//            .document(bookId)
//            .collection("ratings")
//            .get()
//            .await()
//
//        return snapshot.documents.mapNotNull { doc ->
//            val user = doc.getString("user") ?: return@mapNotNull null
//            val rating = doc.getDouble("rating")?.toFloat() ?: return@mapNotNull null
//            val comment = doc.getString("comment") ?: ""
//            Rating(user, rating, comment)
//        }
//    }
//
//    override suspend fun submitRating(bookId: String, rating: Float, comment: String) {
//        val uid = auth.currentUser?.uid ?: throw Exception("Not logged in")
//        val data = mapOf("user" to uid, "rating" to rating, "comment" to comment)
//        firestore.collection("books")
//            .document(bookId)
//            .collection("ratings")
//            .document(uid)
//            .set(data)
//            .await()
//    }
//
//    override suspend fun isFavorite(bookId: String): Boolean {
//        val uid = auth.currentUser?.uid ?: return false
//        val doc = firestore.collection("users")
//            .document(uid)
//            .collection("favorites")
//            .document(bookId)
//            .get()
//            .await()
//        return doc.exists()
//    }
//
//    override suspend fun toggleFavorite(bookId: String): Boolean {
//        val uid = auth.currentUser?.uid ?: throw Exception("Not logged in")
//        val favRef = firestore.collection("users")
//            .document(uid)
//            .collection("favorites")
//            .document(bookId)
//
//        val doc = favRef.get().await()
//        return if (doc.exists()) {
//            favRef.delete().await()
//            false
//        } else {
//            favRef.set(mapOf("timestamp" to FieldValue.serverTimestamp())).await()
//            true
//        }
//    }
//}