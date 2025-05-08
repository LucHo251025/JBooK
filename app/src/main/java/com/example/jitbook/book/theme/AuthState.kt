package com.example.jitbook.book.theme

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
    data class PasswordResetEmailSent(val email: String) : AuthState()
    object PasswordResetSuccess : AuthState()
    data class Success(val message: String) : AuthState()

}