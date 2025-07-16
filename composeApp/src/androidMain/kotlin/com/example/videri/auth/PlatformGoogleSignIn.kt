package com.example.videri.auth

import android.content.Context

actual fun createGoogleSignInHandler(): GoogleSignInHandler {
    // Note: In a real implementation, you would need to pass the context
    // This is a simplified version for demonstration
    throw NotImplementedError("Context required for Android Google Sign-In")
}

fun createGoogleSignInHandler(context: Context): GoogleSignInHandler {
    return AndroidGoogleSignInHandler(context)
}
