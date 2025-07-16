package com.example.videri.auth

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidGoogleSignInHandler(
    private val context: Context,
    private val serverClientId: String? = null
) : GoogleSignInHandler {
    
    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .apply {
                serverClientId?.let { requestIdToken(it) }
            }
            .build()
        
        GoogleSignIn.getClient(context, gso)
    }

    override suspend fun signIn(): GoogleSignInResult {
        return suspendCancellableCoroutine { continuation ->
            val signInIntent = googleSignInClient.signInIntent
            
            // Note: In a real implementation, you would need to handle the activity result
            // This is a simplified version for demonstration
            try {
                val account = GoogleSignIn.getLastSignedInAccount(context)
                if (account != null) {
                    continuation.resume(
                        GoogleSignInResult(
                            isSuccess = true,
                            userEmail = account.email,
                            userDisplayName = account.displayName,
                            userPhotoUrl = account.photoUrl?.toString(),
                            idToken = account.idToken
                        )
                    )
                } else {
                    continuation.resume(
                        GoogleSignInResult(
                            isSuccess = false,
                            errorMessage = "Sign-in failed: No account found"
                        )
                    )
                }
            } catch (e: ApiException) {
                continuation.resume(
                    GoogleSignInResult(
                        isSuccess = false,
                        errorMessage = "Sign-in failed: ${e.message}"
                    )
                )
            }
        }
    }

    override suspend fun signOut() {
        googleSignInClient.signOut()
    }

    override fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(context) != null
    }
}
