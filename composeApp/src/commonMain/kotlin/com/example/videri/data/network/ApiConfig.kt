package com.example.videri.data.network

/**
 * API configuration for The Movie Database (TMDB)
 * 
 * To use this app with real data:
 * 1. Get your free API key from https://www.themoviedb.org/settings/api
 * 2. Replace the placeholder API_KEY below with your actual key
 * 3. Build and run the app
 */
object ApiConfig {
    // Replace with your actual TMDB API key
    const val TMDB_API_KEY = "29231141753f554541606a489eea855a"
    
    // Base URLs
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3"
    const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
    
    // Image sizes
    const val POSTER_SIZE_W342 = "w342"
    const val BACKDROP_SIZE_W780 = "w780"
    const val PROFILE_SIZE_W185 = "w185"
}
