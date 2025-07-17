package com.example.videri.data.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object NetworkClient {
    
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        
        install(Logging) {
            level = LogLevel.INFO
        }
    }
    
    // API Configuration
    object Config {
        const val TMDB_BASE_URL = ApiConfig.TMDB_BASE_URL
        const val TMDB_IMAGE_BASE_URL = ApiConfig.TMDB_IMAGE_BASE_URL
        
        // Use API key from ApiConfig
        const val TMDB_API_KEY = ApiConfig.TMDB_API_KEY
        
        // Image sizes
        const val POSTER_SIZE_W342 = ApiConfig.POSTER_SIZE_W342
        const val BACKDROP_SIZE_W780 = ApiConfig.BACKDROP_SIZE_W780
        const val PROFILE_SIZE_W185 = ApiConfig.PROFILE_SIZE_W185
    }
}
