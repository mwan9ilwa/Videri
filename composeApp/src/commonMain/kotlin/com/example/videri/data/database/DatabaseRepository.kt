package com.example.videri.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.videri.database.VideriDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import com.example.videri.data.models.*

class DatabaseRepository(private val database: VideriDatabase) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    // Helper functions
    private fun generateId(): String = Clock.System.now().toEpochMilliseconds().toString()
    private fun getCurrentDate(): String = Clock.System.todayIn(TimeZone.currentSystemDefault()).toString()
    
    // Movie operations
    suspend fun insertMovie(movie: Movie) {
        database.contentQueries.insertMovie(
            id = movie.id,
            title = movie.title,
            poster_url = movie.posterUrl,
            backdrop_url = movie.backdropUrl,
            rating = movie.rating.toDouble(),
            release_year = movie.releaseYear,
            description = movie.description,
            genres = json.encodeToString(movie.genres),
            runtime = movie.runtime?.toLong(),
            budget = movie.budget,
            box_office = movie.boxOffice,
            cast = json.encodeToString(movie.cast),
            crew = json.encodeToString(movie.crew),
            updated_at = getCurrentDate()
        )
    }
    
    suspend fun getMovieById(id: String): Movie? {
        return database.contentQueries.selectMovieById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .let { flow ->
                var result: Movie? = null
                flow.collect { movieRow ->
                    result = movieRow?.let { row ->
                        Movie(
                            id = row.id,
                            title = row.title,
                            posterUrl = row.poster_url,
                            backdropUrl = row.backdrop_url,
                            rating = row.rating.toFloat(),
                            releaseYear = row.release_year,
                            description = row.description,
                            genres = json.decodeFromString(row.genres),
                            runtime = row.runtime?.toInt(),
                            budget = row.budget,
                            boxOffice = row.box_office,
                            cast = json.decodeFromString(row.cast ?: "[]"),
                            crew = json.decodeFromString(row.crew ?: "[]")
                        )
                    }
                }
                result
            }
    }
    
    fun getAllMovies(): Flow<List<Movie>> {
        return database.contentQueries.selectAllMovies()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .let { flow ->
                kotlinx.coroutines.flow.flow {
                    flow.collect { rows ->
                        val movies = rows.map { row ->
                            Movie(
                                id = row.id,
                                title = row.title,
                                posterUrl = row.poster_url,
                                backdropUrl = row.backdrop_url,
                                rating = row.rating.toFloat(),
                                releaseYear = row.release_year,
                                description = row.description,
                                genres = json.decodeFromString(row.genres),
                                runtime = row.runtime?.toInt(),
                                budget = row.budget,
                                boxOffice = row.box_office,
                                cast = json.decodeFromString(row.cast ?: "[]"),
                                crew = json.decodeFromString(row.crew ?: "[]")
                            )
                        }
                        emit(movies)
                    }
                }
            }
    }
    
    // Watchlist operations
    suspend fun addToWatchlist(contentId: String, contentType: ContentType) {
        database.contentQueries.insertWatchlistItem(
            id = generateId(),
            content_id = contentId,
            content_type = contentType.name,
            added_date = getCurrentDate()
        )
    }
    
    suspend fun removeFromWatchlist(contentId: String, contentType: ContentType) {
        database.contentQueries.removeWatchlistItem(contentId, contentType.name)
    }
    
    fun getWatchlist(): Flow<List<WatchlistItem>> {
        return database.contentQueries.selectWatchlistItems()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .let { flow ->
                kotlinx.coroutines.flow.flow {
                    flow.collect { rows ->
                        val items = rows.map { row ->
                            WatchlistItem(
                                id = row.id,
                                contentId = row.content_id,
                                contentType = ContentType.valueOf(row.content_type),
                                addedDate = row.added_date
                            )
                        }
                        emit(items)
                    }
                }
            }
    }
    
    suspend fun isInWatchlist(contentId: String, contentType: ContentType): Boolean {
        return database.contentQueries.isInWatchlist(contentId, contentType.name)
            .executeAsOne() > 0
    }
    
    // Watched items operations
    suspend fun markAsWatched(
        contentId: String, 
        contentType: ContentType, 
        rating: Float? = null, 
        review: String? = null
    ) {
        database.contentQueries.insertWatchedItem(
            id = generateId(),
            content_id = contentId,
            content_type = contentType.name,
            watched_date = getCurrentDate(),
            user_rating = rating?.toDouble(),
            user_review = review
        )
        
        // Remove from watchlist if it exists
        removeFromWatchlist(contentId, contentType)
    }
    
    suspend fun markAsUnwatched(contentId: String, contentType: ContentType) {
        database.contentQueries.removeWatchedItem(contentId, contentType.name)
    }
    
    suspend fun isWatched(contentId: String, contentType: ContentType): Boolean {
        return database.contentQueries.isWatched(contentId, contentType.name)
            .executeAsOne() > 0
    }
    
    fun getWatchedItems(): Flow<List<WatchedItem>> {
        return database.contentQueries.selectWatchedItems()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .let { flow ->
                kotlinx.coroutines.flow.flow {
                    flow.collect { rows ->
                        val items = rows.map { row ->
                            WatchedItem(
                                id = row.id,
                                contentId = row.content_id,
                                contentType = ContentType.valueOf(row.content_type),
                                watchedDate = row.watched_date,
                                userRating = row.user_rating?.toFloat(),
                                userReview = row.user_review
                            )
                        }
                        emit(items)
                    }
                }
            }
    }
    
    // Custom Lists operations
    suspend fun createCustomList(name: String, description: String?, isPublic: Boolean): CustomList {
        val id = generateId()
        val currentDate = getCurrentDate()
        
        database.contentQueries.insertCustomList(
            id = id,
            name = name,
            description = description,
            is_public = if (isPublic) 1 else 0,
            created_date = currentDate,
            updated_date = currentDate
        )
        
        return CustomList(
            id = id,
            name = name,
            description = description,
            isPublic = isPublic,
            createdDate = currentDate,
            updatedDate = currentDate,
            contentCount = 0
        )
    }
    
    suspend fun addContentToList(listId: String, contentId: String, contentType: ContentType) {
        database.contentQueries.insertCustomListContent(
            id = generateId(),
            list_id = listId,
            content_id = contentId,
            content_type = contentType.name,
            added_date = getCurrentDate()
        )
        
        // Update content count
        database.contentQueries.updateCustomListContentCount(listId, getCurrentDate(), listId)
    }
    
    fun getCustomLists(): Flow<List<CustomList>> {
        return database.contentQueries.selectCustomLists()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .let { flow ->
                kotlinx.coroutines.flow.flow {
                    flow.collect { rows ->
                        val lists = rows.map { row ->
                            CustomList(
                                id = row.id,
                                name = row.name,
                                description = row.description,
                                isPublic = row.is_public == 1L,
                                createdDate = row.created_date,
                                updatedDate = row.updated_date,
                                contentCount = row.content_count.toInt()
                            )
                        }
                        emit(lists)
                    }
                }
            }
    }
    
    // Rating operations
    suspend fun rateContent(contentId: String, contentType: ContentType, rating: Float) {
        val currentDate = getCurrentDate()
        database.contentQueries.insertOrUpdateRating(
            id = "${contentType.name}_$contentId",
            content_id = contentId,
            content_type = contentType.name,
            rating = rating.toDouble(),
            created_date = currentDate,
            updated_date = currentDate
        )
    }
    
    suspend fun getUserRating(contentId: String, contentType: ContentType): Float? {
        return database.contentQueries.selectRating(contentId, contentType.name)
            .executeAsOneOrNull()?.toFloat()
    }
    
    // Review operations
    suspend fun reviewContent(contentId: String, contentType: ContentType, review: String) {
        val currentDate = getCurrentDate()
        database.contentQueries.insertOrUpdateReview(
            id = "${contentType.name}_$contentId",
            content_id = contentId,
            content_type = contentType.name,
            review = review,
            created_date = currentDate,
            updated_date = currentDate
        )
    }
    
    suspend fun getUserReview(contentId: String, contentType: ContentType): String? {
        return database.contentQueries.selectReview(contentId, contentType.name)
            .executeAsOneOrNull()
    }
}
