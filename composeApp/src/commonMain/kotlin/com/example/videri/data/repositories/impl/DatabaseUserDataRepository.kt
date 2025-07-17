package com.example.videri.data.repositories.impl

import com.example.videri.data.database.DatabaseRepository
import com.example.videri.data.models.*
import com.example.videri.data.repositories.UserDataRepository
import kotlinx.coroutines.flow.Flow

class DatabaseUserDataRepository(
    private val databaseRepository: DatabaseRepository
) : UserDataRepository {
    
    override suspend fun addToWatchlist(contentId: String, contentType: ContentType) {
        databaseRepository.addToWatchlist(contentId, contentType)
    }
    
    override suspend fun removeFromWatchlist(contentId: String, contentType: ContentType) {
        databaseRepository.removeFromWatchlist(contentId, contentType)
    }
    
    override suspend fun getWatchlist(): Flow<List<WatchlistItem>> {
        return databaseRepository.getWatchlist()
    }
    
    override suspend fun isInWatchlist(contentId: String, contentType: ContentType): Boolean {
        return databaseRepository.isInWatchlist(contentId, contentType)
    }
    
    override suspend fun markAsWatched(
        contentId: String, 
        contentType: ContentType, 
        rating: Float?, 
        review: String?
    ) {
        databaseRepository.markAsWatched(contentId, contentType, rating, review)
    }
    
    override suspend fun markAsUnwatched(contentId: String, contentType: ContentType) {
        databaseRepository.markAsUnwatched(contentId, contentType)
    }
    
    override suspend fun getWatchedItems(): Flow<List<WatchedItem>> {
        return databaseRepository.getWatchedItems()
    }
    
    override suspend fun isWatched(contentId: String, contentType: ContentType): Boolean {
        return databaseRepository.isWatched(contentId, contentType)
    }
    
    override suspend fun markEpisodeAsWatched(episodeId: String, rating: Float?) {
        // Would implement episode tracking in database
        // For now, using simple implementation
    }
    
    override suspend fun markEpisodeAsUnwatched(episodeId: String) {
        // Would implement episode tracking in database
    }
    
    override suspend fun getWatchedEpisodes(tvShowId: String): Flow<List<Episode>> {
        return kotlinx.coroutines.flow.flowOf(emptyList())
    }
    
    override suspend fun rateContent(contentId: String, contentType: ContentType, rating: Float) {
        databaseRepository.rateContent(contentId, contentType, rating)
    }
    
    override suspend fun reviewContent(contentId: String, contentType: ContentType, review: String) {
        databaseRepository.reviewContent(contentId, contentType, review)
    }
    
    override suspend fun getUserRating(contentId: String, contentType: ContentType): Float? {
        return databaseRepository.getUserRating(contentId, contentType)
    }
    
    override suspend fun getUserReview(contentId: String, contentType: ContentType): String? {
        return databaseRepository.getUserReview(contentId, contentType)
    }
    
    override suspend fun updateUserPreferences(preferences: UserPreferences) {
        // Would implement user preferences in database
    }
    
    override suspend fun getUserPreferences(): Flow<UserPreferences> {
        return kotlinx.coroutines.flow.flowOf(UserPreferences())
    }
}
