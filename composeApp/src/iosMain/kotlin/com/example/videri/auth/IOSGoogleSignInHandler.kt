package com.example.videri.auth

class IOSGoogleSignInHandler : GoogleSignInHandler {
    
    override suspend fun signIn(): GoogleSignInResult {
        // TODO: Implement iOS Google Sign-In using Google Sign-In SDK for iOS
        return GoogleSignInResult(
            isSuccess = false,
            errorMessage = "Google Sign-In not implemented for iOS yet"
        )
    }

    override suspend fun signOut() {
        // TODO: Implement iOS sign-out
    }

    override fun isSignedIn(): Boolean {
        // TODO: Implement iOS sign-in check
        return false
    }
}
