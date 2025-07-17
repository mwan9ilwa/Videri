package com.example.videri.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.videri.data.models.*
import com.example.videri.data.repositories.ContentRepository
import com.example.videri.data.repositories.UserDataRepository

class MovieDetailViewModel(
    private val contentRepository: ContentRepository,
    private val userDataRepository: UserDataRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    
    var uiState by mutableStateOf(MovieDetailUiState())
        private set
    
    fun loadMovie(movieId: String) {
        uiState = uiState.copy(isLoading = true)
        
        coroutineScope.launch {
            try {
                val movie = contentRepository.getMovie(movieId)
                if (movie != null) {
                    val isWatched = userDataRepository.isWatched(movieId, ContentType.MOVIE)
                    val isInWatchlist = userDataRepository.isInWatchlist(movieId, ContentType.MOVIE)
                    val userRating = userDataRepository.getUserRating(movieId, ContentType.MOVIE)
                    val userReview = userDataRepository.getUserReview(movieId, ContentType.MOVIE)
                    
                    uiState = uiState.copy(
                        movie = movie,
                        isWatched = isWatched,
                        isInWatchlist = isInWatchlist,
                        userRating = userRating ?: 0f,
                        userReview = userReview ?: "",
                        isLoading = false,
                        error = null
                    )
                    
                    // Load similar movies
                    loadSimilarMovies(movieId)
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "Movie not found"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }
    
    private fun loadSimilarMovies(movieId: String) {
        coroutineScope.launch {
            try {
                val similarContent = contentRepository.getSimilarContent(ContentType.MOVIE, movieId)
                val similarMovies = similarContent.filterIsInstance<Movie>()
                uiState = uiState.copy(similarMovies = similarMovies)
            } catch (e: Exception) {
                // Don't update error state for similar movies, just log or handle silently
            }
        }
    }
    
    fun toggleWatchlist() {
        val movie = uiState.movie ?: return
        
        coroutineScope.launch {
            try {
                if (uiState.isInWatchlist) {
                    userDataRepository.removeFromWatchlist(movie.id, ContentType.MOVIE)
                } else {
                    userDataRepository.addToWatchlist(movie.id, ContentType.MOVIE)
                }
                uiState = uiState.copy(isInWatchlist = !uiState.isInWatchlist)
            } catch (e: Exception) {
                // Handle error - could show a snackbar or toast
            }
        }
    }
    
    fun toggleWatched() {
        val movie = uiState.movie ?: return
        
        coroutineScope.launch {
            try {
                if (uiState.isWatched) {
                    userDataRepository.markAsUnwatched(movie.id, ContentType.MOVIE)
                } else {
                    userDataRepository.markAsWatched(
                        movie.id, 
                        ContentType.MOVIE,
                        rating = if (uiState.userRating > 0) uiState.userRating else null,
                        review = if (uiState.userReview.isNotBlank()) uiState.userReview else null
                    )
                }
                uiState = uiState.copy(isWatched = !uiState.isWatched)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun updateUserRating(rating: Float) {
        val movie = uiState.movie ?: return
        
        uiState = uiState.copy(userRating = rating)
        
        coroutineScope.launch {
            try {
                userDataRepository.rateContent(movie.id, ContentType.MOVIE, rating)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun updateUserReview(review: String) {
        val movie = uiState.movie ?: return
        
        uiState = uiState.copy(userReview = review)
        
        coroutineScope.launch {
            try {
                if (review.isNotBlank()) {
                    userDataRepository.reviewContent(movie.id, ContentType.MOVIE, review)
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

data class MovieDetailUiState(
    val movie: Movie? = null,
    val isWatched: Boolean = false,
    val isInWatchlist: Boolean = false,
    val userRating: Float = 0f,
    val userReview: String = "",
    val similarMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
