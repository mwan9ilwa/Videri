package com.example.videri.data.repositories.impl

import com.example.videri.data.models.*
import com.example.videri.data.repositories.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class MockUserDataRepository : UserDataRepository {
    
    private val _watchlist = MutableStateFlow<List<WatchlistItem>>(emptyList())
    private val _watchedItems = MutableStateFlow<List<WatchedItem>>(emptyList())
    private val _watchedEpisodes = MutableStateFlow<Map<String, List<Episode>>>(emptyMap())
    private val _userPreferences = MutableStateFlow(UserPreferences())
    private val _userRatings = MutableStateFlow<Map<String, Float>>(emptyMap())
    private val _userReviews = MutableStateFlow<Map<String, String>>(emptyMap())
    
    private fun generateId(): String = Clock.System.now().toEpochMilliseconds().toString()
    private fun getCurrentDate(): String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
    
    override suspend fun addToWatchlist(contentId: String, contentType: ContentType) {
        val currentList = _watchlist.value.toMutableList()
        val existingItem = currentList.find { it.contentId == contentId && it.contentType == contentType }
        
        if (existingItem == null) {
            val newItem = WatchlistItem(
                id = generateId(),
                contentId = contentId,
                contentType = contentType,
                addedDate = getCurrentDate()
            )
            currentList.add(newItem)
            _watchlist.value = currentList
        }
    }
    
    override suspend fun removeFromWatchlist(contentId: String, contentType: ContentType) {
        val currentList = _watchlist.value.toMutableList()
        currentList.removeAll { it.contentId == contentId && it.contentType == contentType }
        _watchlist.value = currentList
    }
    
    override suspend fun getWatchlist(): Flow<List<WatchlistItem>> = _watchlist.asStateFlow()
    
    override suspend fun isInWatchlist(contentId: String, contentType: ContentType): Boolean {
        return _watchlist.value.any { it.contentId == contentId && it.contentType == contentType }
    }
    
    override suspend fun markAsWatched(
        contentId: String, 
        contentType: ContentType, 
        rating: Float?, 
        review: String?
    ) {
        val currentList = _watchedItems.value.toMutableList()
        val existingItem = currentList.find { it.contentId == contentId && it.contentType == contentType }
        
        if (existingItem == null) {
            val newItem = WatchedItem(
                id = generateId(),
                contentId = contentId,
                contentType = contentType,
                watchedDate = getCurrentDate(),
                userRating = rating,
                userReview = review
            )
            currentList.add(newItem)
            _watchedItems.value = currentList
            
            // Remove from watchlist if it exists
            removeFromWatchlist(contentId, contentType)
            
            // Save rating and review separately
            rating?.let { rateContent(contentId, contentType, it) }
            review?.let { reviewContent(contentId, contentType, it) }
        }
    }
    
    override suspend fun markAsUnwatched(contentId: String, contentType: ContentType) {
        val currentList = _watchedItems.value.toMutableList()
        currentList.removeAll { it.contentId == contentId && it.contentType == contentType }
        _watchedItems.value = currentList
    }
    
    override suspend fun getWatchedItems(): Flow<List<WatchedItem>> = _watchedItems.asStateFlow()
    
    override suspend fun isWatched(contentId: String, contentType: ContentType): Boolean {
        return _watchedItems.value.any { it.contentId == contentId && it.contentType == contentType }
    }
    
    override suspend fun markEpisodeAsWatched(episodeId: String, rating: Float?) {
        val currentEpisodes = _watchedEpisodes.value.toMutableMap()
        
        // For simplicity, we'll extract the show ID from episode ID format "s1e1" -> "1"
        val showId = episodeId.split("e")[0].removePrefix("s")
        val showEpisodes = currentEpisodes[showId]?.toMutableList() ?: mutableListOf()
        
        // Find and update the episode
        val episodeIndex = showEpisodes.indexOfFirst { it.id == episodeId }
        if (episodeIndex != -1) {
            showEpisodes[episodeIndex] = showEpisodes[episodeIndex].copy(
                isWatched = true,
                watchedDate = getCurrentDate(),
                userRating = rating
            )
        } else {
            // Create a new episode entry if it doesn't exist
            val parts = episodeId.split("e")
            val seasonNumber = parts[0].removePrefix("s").toIntOrNull() ?: 1
            val episodeNumber = parts[1].toIntOrNull() ?: 1
            
            val newEpisode = Episode(
                id = episodeId,
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber,
                title = "Episode $episodeNumber",
                isWatched = true,
                watchedDate = getCurrentDate(),
                userRating = rating
            )
            showEpisodes.add(newEpisode)
        }
        
        currentEpisodes[showId] = showEpisodes
        _watchedEpisodes.value = currentEpisodes
    }
    
    override suspend fun markEpisodeAsUnwatched(episodeId: String) {
        val currentEpisodes = _watchedEpisodes.value.toMutableMap()
        val showId = episodeId.split("e")[0].removePrefix("s")
        val showEpisodes = currentEpisodes[showId]?.toMutableList()
        
        showEpisodes?.let { episodes ->
            val episodeIndex = episodes.indexOfFirst { it.id == episodeId }
            if (episodeIndex != -1) {
                episodes[episodeIndex] = episodes[episodeIndex].copy(
                    isWatched = false,
                    watchedDate = null,
                    userRating = null
                )
                currentEpisodes[showId] = episodes
                _watchedEpisodes.value = currentEpisodes
            }
        }
    }
    
    override suspend fun getWatchedEpisodes(tvShowId: String): Flow<List<Episode>> {
        return kotlinx.coroutines.flow.flow {
            _watchedEpisodes.collect { episodesMap ->
                emit(episodesMap[tvShowId] ?: emptyList())
            }
        }
    }
    
    override suspend fun rateContent(contentId: String, contentType: ContentType, rating: Float) {
        val key = "${contentType.name}_$contentId"
        val currentRatings = _userRatings.value.toMutableMap()
        currentRatings[key] = rating
        _userRatings.value = currentRatings
    }
    
    override suspend fun reviewContent(contentId: String, contentType: ContentType, review: String) {
        val key = "${contentType.name}_$contentId"
        val currentReviews = _userReviews.value.toMutableMap()
        currentReviews[key] = review
        _userReviews.value = currentReviews
    }
    
    override suspend fun getUserRating(contentId: String, contentType: ContentType): Float? {
        val key = "${contentType.name}_$contentId"
        return _userRatings.value[key]
    }
    
    override suspend fun getUserReview(contentId: String, contentType: ContentType): String? {
        val key = "${contentType.name}_$contentId"
        return _userReviews.value[key]
    }
    
    override suspend fun updateUserPreferences(preferences: UserPreferences) {
        _userPreferences.value = preferences
    }
    
    override suspend fun getUserPreferences(): Flow<UserPreferences> = _userPreferences.asStateFlow()
}
