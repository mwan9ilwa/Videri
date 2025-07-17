package com.example.videri.data.repositories

import com.example.videri.data.models.*
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    suspend fun getMovie(id: String): Movie?
    suspend fun getTVShow(id: String): TVShow?
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
    suspend fun getPopularTVShows(page: Int = 1): List<TVShow>
    suspend fun getTrendingMovies(): List<Movie>
    suspend fun getTrendingTVShows(): List<TVShow>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getTopRatedTVShows(): List<TVShow>
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun searchTVShows(query: String): List<TVShow>
    suspend fun getMoviesByGenre(genre: String): List<Movie>
    suspend fun getTVShowsByGenre(genre: String): List<TVShow>
    suspend fun getRecommendations(contentType: ContentType, contentId: String): List<Any>
    suspend fun getSimilarContent(contentType: ContentType, contentId: String): List<Any>
}

interface UserDataRepository {
    // Watchlist
    suspend fun addToWatchlist(contentId: String, contentType: ContentType)
    suspend fun removeFromWatchlist(contentId: String, contentType: ContentType)
    suspend fun getWatchlist(): Flow<List<WatchlistItem>>
    suspend fun isInWatchlist(contentId: String, contentType: ContentType): Boolean
    
    // Watched items
    suspend fun markAsWatched(contentId: String, contentType: ContentType, rating: Float? = null, review: String? = null)
    suspend fun markAsUnwatched(contentId: String, contentType: ContentType)
    suspend fun getWatchedItems(): Flow<List<WatchedItem>>
    suspend fun isWatched(contentId: String, contentType: ContentType): Boolean
    
    // Episode tracking
    suspend fun markEpisodeAsWatched(episodeId: String, rating: Float? = null)
    suspend fun markEpisodeAsUnwatched(episodeId: String)
    suspend fun getWatchedEpisodes(tvShowId: String): Flow<List<Episode>>
    
    // Ratings and reviews
    suspend fun rateContent(contentId: String, contentType: ContentType, rating: Float)
    suspend fun reviewContent(contentId: String, contentType: ContentType, review: String)
    suspend fun getUserRating(contentId: String, contentType: ContentType): Float?
    suspend fun getUserReview(contentId: String, contentType: ContentType): String?
    
    // User preferences
    suspend fun updateUserPreferences(preferences: UserPreferences)
    suspend fun getUserPreferences(): Flow<UserPreferences>
}

interface CustomListRepository {
    suspend fun createList(name: String, description: String?, isPublic: Boolean): CustomList
    suspend fun updateList(list: CustomList): CustomList
    suspend fun deleteList(listId: String)
    suspend fun getUserLists(): Flow<List<CustomList>>
    suspend fun getListById(listId: String): CustomList?
    suspend fun addContentToList(listId: String, contentId: String, contentType: ContentType)
    suspend fun removeContentFromList(listId: String, contentId: String, contentType: ContentType)
    suspend fun getListContent(listId: String): Flow<List<Any>> // Mix of Movies and TVShows
}

interface RecommendationRepository {
    suspend fun getPersonalizedRecommendations(): List<Any> // Mix of Movies and TVShows
    suspend fun getRecommendationsByGenre(genre: String): List<Any>
    suspend fun getRecommendationsByMood(mood: String): List<Any>
    suspend fun getTrendingRecommendations(): List<Any>
    suspend fun refreshRecommendations()
}
