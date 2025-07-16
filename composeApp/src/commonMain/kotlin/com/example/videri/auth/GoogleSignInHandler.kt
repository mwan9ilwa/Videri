package com.example.videri.auth

/**
 * Interface for Google Sign-In functionality
 * Platform-specific implementations will handle the actual sign-in process
 */
interface GoogleSignInHandler {
    suspend fun signIn(): GoogleSignInResult
    suspend fun signOut()
    fun isSignedIn(): Boolean
}

data class GoogleSignInResult(
    val isSuccess: Boolean,
    val userEmail: String? = null,
    val userDisplayName: String? = null,
    val userPhotoUrl: String? = null,
    val idToken: String? = null,
    val errorMessage: String? = null
)
