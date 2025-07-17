package com.example.videri.data.repositories.impl

import com.example.videri.data.database.DatabaseRepository
import com.example.videri.data.mappers.TMDBMapper
import com.example.videri.data.models.*
import com.example.videri.data.network.api.TMDBApiService
import com.example.videri.data.repositories.ContentRepository
import kotlinx.coroutines.flow.first

class NetworkContentRepository(
    private val apiService: TMDBApiService,
    private val databaseRepository: DatabaseRepository
) : ContentRepository {
    
    override suspend fun getMovie(id: String): Movie? {
        return try {
            // First try to get from local database
            val localMovie = databaseRepository.getMovieById(id)
            if (localMovie != null) {
                return localMovie
            }
            
            // If not found locally, fetch from API
            val movieDetails = apiService.getMovieDetails(id.toInt())
            val credits = try {
                apiService.getMovieCredits(id.toInt())
            } catch (e: Exception) {
                null
            }
            
            val movie = TMDBMapper.mapMovieDetails(movieDetails, credits)
            
            // Cache in database
            databaseRepository.insertMovie(movie)
            
            movie
        } catch (e: Exception) {
            // Fallback to database if API fails
            databaseRepository.getMovieById(id)
        }
    }
    
    override suspend fun getTVShow(id: String): TVShow? {
        return try {
            // Similar pattern for TV shows - would need to implement TV show database operations
            val tvDetails = apiService.getTVShowDetails(id.toInt())
            val credits = try {
                apiService.getTVShowCredits(id.toInt())
            } catch (e: Exception) {
                null
            }
            
            TMDBMapper.mapTVShowDetails(tvDetails, credits)
        } catch (e: Exception) {
            null
        }
    }
    
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        return try {
            val response = apiService.getPopularMovies(page)
            val movies = response.results.map { 
                TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
            }
            
            // Cache movies in database
            movies.forEach { movie ->
                databaseRepository.insertMovie(movie)
            }
            
            movies
        } catch (e: Exception) {
            // Fallback to cached data
            databaseRepository.getAllMovies().first()
        }
    }
    
    override suspend fun getPopularTVShows(page: Int): List<TVShow> {
        return try {
            val response = apiService.getPopularTVShows(page)
            response.results.map { 
                TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getTrendingMovies(): List<Movie> {
        return try {
            val response = apiService.getTrendingMovies()
            val movies = response.results.map { 
                TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
            }
            
            // Cache trending movies
            movies.forEach { movie ->
                databaseRepository.insertMovie(movie)
            }
            
            movies
        } catch (e: Exception) {
            // Return cached movies as fallback
            databaseRepository.getAllMovies().first().take(10)
        }
    }
    
    override suspend fun getTrendingTVShows(): List<TVShow> {
        return try {
            val response = apiService.getTrendingTVShows()
            response.results.map { 
                TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getTopRatedMovies(): List<Movie> {
        return try {
            val response = apiService.getTopRatedMovies()
            response.results.map { 
                TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getTopRatedTVShows(): List<TVShow> {
        return try {
            val response = apiService.getTopRatedTVShows()
            response.results.map { 
                TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun searchMovies(query: String): List<Movie> {
        return try {
            val response = apiService.searchMovies(query)
            response.results.map { 
                TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun searchTVShows(query: String): List<TVShow> {
        return try {
            val response = apiService.searchTVShows(query)
            response.results.map { 
                TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getMoviesByGenre(genre: String): List<Movie> {
        return try {
            // Find genre ID from name
            val genreId = TMDBMapper.movieGenres.entries
                .find { it.value.equals(genre, ignoreCase = true) }?.key
                
            if (genreId != null) {
                val response = apiService.discoverMoviesByGenre(genreId)
                response.results.map { 
                    TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getTVShowsByGenre(genre: String): List<TVShow> {
        return try {
            // Find genre ID from name
            val genreId = TMDBMapper.tvGenres.entries
                .find { it.value.equals(genre, ignoreCase = true) }?.key
                
            if (genreId != null) {
                val response = apiService.discoverTVShowsByGenre(genreId)
                response.results.map { 
                    TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getRecommendations(contentType: ContentType, contentId: String): List<Any> {
        return try {
            when (contentType) {
                ContentType.MOVIE -> {
                    val response = apiService.getSimilarMovies(contentId.toInt())
                    response.results.take(5).map { 
                        TMDBMapper.mapMovie(it, TMDBMapper.movieGenres)
                    }
                }
                ContentType.TV_SHOW -> {
                    val response = apiService.getSimilarTVShows(contentId.toInt())
                    response.results.take(5).map { 
                        TMDBMapper.mapTVShow(it, TMDBMapper.tvGenres)
                    }
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun getSimilarContent(contentType: ContentType, contentId: String): List<Any> {
        return getRecommendations(contentType, contentId)
    }
}
