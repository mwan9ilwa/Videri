package com.example.videri.data.network.api

import com.example.videri.data.network.NetworkClient
import com.example.videri.data.network.models.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TMDBApiService {
    
    private val client = NetworkClient.httpClient
    private val baseUrl = NetworkClient.Config.TMDB_BASE_URL
    private val apiKey = NetworkClient.Config.TMDB_API_KEY
    
    // Movies
    suspend fun getPopularMovies(page: Int = 1): TMDBMovieResponse {
        return client.get("$baseUrl/movie/popular") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }.body()
    }
    
    suspend fun getTopRatedMovies(page: Int = 1): TMDBMovieResponse {
        return client.get("$baseUrl/movie/top_rated") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }.body()
    }
    
    suspend fun getTrendingMovies(): TMDBMovieResponse {
        return client.get("$baseUrl/trending/movie/day") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getMovieDetails(movieId: Int): TMDBMovieDetails {
        return client.get("$baseUrl/movie/$movieId") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getMovieCredits(movieId: Int): TMDBCredits {
        return client.get("$baseUrl/movie/$movieId/credits") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getSimilarMovies(movieId: Int): TMDBMovieResponse {
        return client.get("$baseUrl/movie/$movieId/similar") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun searchMovies(query: String, page: Int = 1): TMDBMovieResponse {
        return client.get("$baseUrl/search/movie") {
            parameter("api_key", apiKey)
            parameter("query", query)
            parameter("page", page)
        }.body()
    }
    
    // TV Shows
    suspend fun getPopularTVShows(page: Int = 1): TMDBTVResponse {
        return client.get("$baseUrl/tv/popular") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }.body()
    }
    
    suspend fun getTopRatedTVShows(page: Int = 1): TMDBTVResponse {
        return client.get("$baseUrl/tv/top_rated") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }.body()
    }
    
    suspend fun getTrendingTVShows(): TMDBTVResponse {
        return client.get("$baseUrl/trending/tv/day") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getTVShowDetails(tvId: Int): TMDBTVDetails {
        return client.get("$baseUrl/tv/$tvId") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getTVShowCredits(tvId: Int): TMDBCredits {
        return client.get("$baseUrl/tv/$tvId/credits") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun getSimilarTVShows(tvId: Int): TMDBTVResponse {
        return client.get("$baseUrl/tv/$tvId/similar") {
            parameter("api_key", apiKey)
        }.body()
    }
    
    suspend fun searchTVShows(query: String, page: Int = 1): TMDBTVResponse {
        return client.get("$baseUrl/search/tv") {
            parameter("api_key", apiKey)
            parameter("query", query)
            parameter("page", page)
        }.body()
    }
    
    // Discover
    suspend fun discoverMoviesByGenre(genreId: Int, page: Int = 1): TMDBMovieResponse {
        return client.get("$baseUrl/discover/movie") {
            parameter("api_key", apiKey)
            parameter("with_genres", genreId)
            parameter("page", page)
        }.body()
    }
    
    suspend fun discoverTVShowsByGenre(genreId: Int, page: Int = 1): TMDBTVResponse {
        return client.get("$baseUrl/discover/tv") {
            parameter("api_key", apiKey)
            parameter("with_genres", genreId)
            parameter("page", page)
        }.body()
    }
    
    // Utility functions
    fun getImageUrl(path: String?, size: String = NetworkClient.Config.POSTER_SIZE_W342): String? {
        return if (path != null) {
            "${NetworkClient.Config.TMDB_IMAGE_BASE_URL}/$size$path"
        } else null
    }
}
