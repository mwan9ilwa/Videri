package com.example.videri.data.repositories

import com.example.videri.data.models.AuthResult
import com.example.videri.data.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    // Authentication state
    val currentUser: Flow<User?>
    val isAuthenticated: Flow<Boolean>
    
    // Authentication methods
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String, displayName: String): AuthResult
    suspend fun signInWithGoogle(): AuthResult
    suspend fun signOut(): Boolean
    
    // User management
    suspend fun getCurrentUser(): User?
    suspend fun updateUserProfile(user: User): AuthResult
    suspend fun deleteAccount(): Boolean
    
    // Password management
    suspend fun resetPassword(email: String): Boolean
    suspend fun changePassword(oldPassword: String, newPassword: String): AuthResult
}
