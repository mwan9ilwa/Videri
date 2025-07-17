package com.example.videri.data.repositories.impl

import com.example.videri.data.models.*
import com.example.videri.data.repositories.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class MockAuthRepository : AuthRepository {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()
    override val isAuthenticated: Flow<Boolean> = currentUser.map { it != null }
    
    // Mock users for testing
    private val mockUsers = mutableMapOf(
        "test@example.com" to Pair("password123", createMockUser("test@example.com", "Test User")),
        "john@example.com" to Pair("password456", createMockUser("john@example.com", "John Doe")),
        "jane@example.com" to Pair("password789", createMockUser("jane@example.com", "Jane Smith"))
    )
    
    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        delay(1000) // Simulate network delay
        
        val userPair = mockUsers[email]
        return if (userPair != null && userPair.first == password) {
            val user = userPair.second.copy(
                lastLoginAt = Clock.System.now().toString()
            )
            _currentUser.value = user
            AuthResult(user = user)
        } else {
            AuthResult(error = "Invalid email or password")
        }
    }
    
    override suspend fun signUpWithEmail(email: String, password: String, displayName: String): AuthResult {
        delay(1000) // Simulate network delay
        
        return if (mockUsers.containsKey(email)) {
            AuthResult(error = "Email already exists")
        } else {
            val user = createMockUser(email, displayName)
            mockUsers[email] = Pair(password, user)
            _currentUser.value = user
            AuthResult(user = user)
        }
    }
    
    override suspend fun signInWithGoogle(): AuthResult {
        delay(1500) // Simulate OAuth flow
        
        val user = createMockUser("google.user@example.com", "Google User")
        _currentUser.value = user
        return AuthResult(user = user)
    }
    
    override suspend fun signOut(): Boolean {
        delay(500)
        _currentUser.value = null
        return true
    }
    
    override suspend fun getCurrentUser(): User? {
        return _currentUser.value
    }
    
    override suspend fun updateUserProfile(user: User): AuthResult {
        delay(800)
        
        val currentUser = _currentUser.value
        return if (currentUser?.id == user.id) {
            _currentUser.value = user
            
            // Update in mock storage
            mockUsers.entries.find { it.value.second.id == user.id }?.let { entry ->
                mockUsers[entry.key] = entry.value.copy(second = user)
            }
            
            AuthResult(user = user)
        } else {
            AuthResult(error = "User not found")
        }
    }
    
    override suspend fun deleteAccount(): Boolean {
        delay(1000)
        
        val currentUser = _currentUser.value
        if (currentUser != null) {
            // Remove from mock storage
            mockUsers.entries.removeAll { it.value.second.id == currentUser.id }
            _currentUser.value = null
            return true
        }
        return false
    }
    
    override suspend fun resetPassword(email: String): Boolean {
        delay(1000)
        return mockUsers.containsKey(email)
    }
    
    override suspend fun changePassword(oldPassword: String, newPassword: String): AuthResult {
        delay(800)
        
        val currentUser = _currentUser.value
        if (currentUser != null) {
            val userEntry = mockUsers.entries.find { it.value.second.id == currentUser.id }
            if (userEntry != null && userEntry.value.first == oldPassword) {
                mockUsers[userEntry.key] = userEntry.value.copy(first = newPassword)
                return AuthResult(user = currentUser)
            } else {
                return AuthResult(error = "Current password is incorrect")
            }
        }
        return AuthResult(error = "User not authenticated")
    }
    
    private fun createMockUser(email: String, displayName: String): User {
        return User(
            id = "mock_${email.hashCode()}",
            email = email,
            displayName = displayName,
            profileImageUrl = "https://api.dicebear.com/7.x/avataaars/png?seed=$displayName",
            createdAt = Clock.System.now().toString(),
            lastLoginAt = Clock.System.now().toString()
        )
    }
}
