package com.example.videri.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.videri.data.models.AuthResult
import com.example.videri.data.models.User
import com.example.videri.data.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val coroutineScope: CoroutineScope
) {
    // UI State
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    // User state from repository
    val currentUser = authRepository.currentUser
    val isAuthenticated = authRepository.isAuthenticated
    
    // Form validation
    var isFormValid by mutableStateOf(false)
        private set
    
    fun signInWithEmail(email: String, password: String) {
        coroutineScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.signInWithEmail(email, password)
            
            if (!result.isSuccess) {
                _errorMessage.value = result.error
            }
            
            _isLoading.value = false
        }
    }
    
    fun signUpWithEmail(email: String, password: String, displayName: String) {
        coroutineScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.signUpWithEmail(email, password, displayName)
            
            if (!result.isSuccess) {
                _errorMessage.value = result.error
            }
            
            _isLoading.value = false
        }
    }
    
    fun signInWithGoogle() {
        coroutineScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.signInWithGoogle()
            
            if (!result.isSuccess) {
                _errorMessage.value = result.error
            }
            
            _isLoading.value = false
        }
    }
    
    fun signOut() {
        coroutineScope.launch {
            _isLoading.value = true
            val success = authRepository.signOut()
            if (!success) {
                _errorMessage.value = "Failed to sign out"
            }
            _isLoading.value = false
        }
    }
    
    fun resetPassword(email: String) {
        coroutineScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val success = authRepository.resetPassword(email)
            if (success) {
                _errorMessage.value = "Password reset email sent"
            } else {
                _errorMessage.value = "Email not found"
            }
            
            _isLoading.value = false
        }
    }
    
    fun updateProfile(user: User) {
        coroutineScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.updateUserProfile(user)
            
            if (!result.isSuccess) {
                _errorMessage.value = result.error
            }
            
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    fun validateForm(email: String, password: String, confirmPassword: String? = null): Boolean {
        val isEmailValid = email.contains("@") && email.contains(".") && email.length > 5
        val isPasswordValid = password.length >= 6
        val doPasswordsMatch = confirmPassword?.let { it == password } ?: true
        
        isFormValid = isEmailValid && isPasswordValid && doPasswordsMatch
        return isFormValid
    }
}
