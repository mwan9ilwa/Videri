package com.example.videri.auth

actual fun createGoogleSignInHandler(): GoogleSignInHandler {
    return IOSGoogleSignInHandler()
}
