package com.example.jitbook.book.theme.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jitbook.book.theme.AuthState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import com.google.firebase.functions.functions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    private val _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    var isLoading by mutableStateOf(false)
        private set

    // Thêm vào đầu ViewModel
    var savedOtp by mutableStateOf<String?>(null)
        private set

    fun updateOtp(otp: String) {
        savedOtp = otp
    }



    init {
        _currentUser.value = auth.currentUser
    }



    fun checkAuthState() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    private fun updateUserOnSuccess() {
        _currentUser.value = auth.currentUser
        _authState.value = AuthState.Authenticated
    }

    fun login(email: String, password: String) {

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUserOnSuccess()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }

    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _authState.value = AuthState.Error("Email cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.PasswordResetEmailSent(email)
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Failed to send reset email")
                }
            }
    }

    fun updateProfile(name: String, photoUrl: String?) {
        val user = auth.currentUser ?: return

        val builder = UserProfileChangeRequest.Builder()
            .setDisplayName(name)

        // Chỉ set PhotoUri nếu là chuỗi hợp lệ và không rỗng
        if (!photoUrl.isNullOrBlank() && photoUrl != "null") {
            try {
                val uri = Uri.parse(photoUrl)
                if (uri.toString().isNotBlank()) {
                    builder.setPhotoUri(uri)
                }
            } catch (e: Exception) {
                _authState.postValue(AuthState.Error("Ảnh không hợp lệ: ${e.message}"))
                return
            }
        }

        val profileUpdates = builder.build()

        user.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.reload().addOnSuccessListener {
                    _currentUser.postValue(auth.currentUser)
                    _authState.postValue(AuthState.Success("Cập nhật thành công"))
                }.addOnFailureListener {
                    _authState.postValue(AuthState.Error("Reload thất bại: ${it.message}"))
                }
            } else {
                _authState.postValue(AuthState.Error("Cập nhật thất bại: ${task.exception?.message}"))
            }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _authState.postValue(AuthState.Success("Đã đăng xuất"))
        _currentUser.postValue(null)
    }

    fun signInWithGoogle(idToken: String) {
        _authState.value = AuthState.Loading

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUserOnSuccess()
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Google sign-in failed")
                }
            }
    }

    fun confirmPasswordReset(code: String, newPassword: String) {
        _authState.value = AuthState.Loading
        FirebaseAuth.getInstance().confirmPasswordReset(code, newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.PasswordResetSuccess
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Failed to reset password")
                }
            }
    }

}