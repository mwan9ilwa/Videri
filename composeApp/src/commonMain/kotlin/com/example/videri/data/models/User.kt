package com.example.videri.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val profileImageUrl: String? = null,
    val createdAt: String,
    val lastLoginAt: String? = null,
    val preferences: AppUserPreferences = AppUserPreferences()
)

@Serializable
data class AppUserPreferences(
    val theme: String = "auto", // "light", "dark", "auto"
    val language: String = "en",
    val notifications: NotificationSettings = NotificationSettings()
)

@Serializable
data class NotificationSettings(
    val newEpisodes: Boolean = true,
    val recommendations: Boolean = true,
    val listUpdates: Boolean = false,
    val socialActivity: Boolean = false
)

@Serializable
data class AuthResult(
    val user: User? = null,
    val error: String? = null,
    val isSuccess: Boolean = user != null
)
