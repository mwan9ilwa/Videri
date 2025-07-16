package com.example.videri.examples

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import com.example.videri.auth.GoogleSignInHandler
import com.example.videri.auth.GoogleSignInResult
import com.example.videri.ui.screens.auth.LoginScreen

/**
 * Example of how to integrate Google Sign-In with the LoginScreen
 * 
 * This demonstrates:
 * 1. Managing Google Sign-In state
 * 2. Handling sign-in results
 * 3. Error handling
 * 4. Loading states
 */
@Composable
fun GoogleSignInExample(
    googleSignInHandler: GoogleSignInHandler,
    onAuthSuccess: (GoogleSignInResult) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var isGoogleSignInLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val coroutineScope = rememberCoroutineScope()

    LoginScreen(
        onLoginClick = { email, password ->
            isLoading = true
            errorMessage = null
            // TODO: Implement regular email/password login
            // This would typically involve calling your backend API
            coroutineScope.launch {
                try {
                    // Simulate login
                    kotlinx.coroutines.delay(1000)
                    isLoading = false
                    // onAuthSuccess(...)
                } catch (e: Exception) {
                    isLoading = false
                    errorMessage = "Login failed: ${e.message}"
                }
            }
        },
        onSignUpClick = onNavigateToSignUp,
        onForgotPasswordClick = onNavigateToForgotPassword,
        onGoogleSignInClick = {
            isGoogleSignInLoading = true
            errorMessage = null
            
            coroutineScope.launch {
                try {
                    val result = googleSignInHandler.signIn()
                    isGoogleSignInLoading = false
                    
                    if (result.isSuccess) {
                        onAuthSuccess(result)
                    } else {
                        errorMessage = result.errorMessage ?: "Google Sign-In failed"
                    }
                } catch (e: Exception) {
                    isGoogleSignInLoading = false
                    errorMessage = "Google Sign-In error: ${e.message}"
                }
            }
        },
        isLoading = isLoading,
        isGoogleSignInLoading = isGoogleSignInLoading,
        errorMessage = errorMessage
    )
}

/**
 * Example of how to handle Google Sign-In result
 */
fun handleGoogleSignInSuccess(result: GoogleSignInResult) {
    println("Google Sign-In Success!")
    println("Email: ${result.userEmail}")
    println("Display Name: ${result.userDisplayName}")
    println("Photo URL: ${result.userPhotoUrl}")
    
    // TODO: Send the ID token to your backend for verification
    result.idToken?.let { token ->
        // sendTokenToBackend(token)
    }
}
