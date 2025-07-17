package com.example.videri.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val rating: Float,
    val releaseYear: String,
    val description: String,
    val genres: List<String>,
    val runtime: Int? = null, // in minutes
    val budget: Long? = null,
    val boxOffice: Long? = null,
    val cast: List<CastMember> = emptyList(),
    val crew: List<CrewMember> = emptyList(),
    val isWatched: Boolean = false,
    val isInWatchlist: Boolean = false,
    val userRating: Float? = null,
    val userReview: String? = null,
    val watchedDate: String? = null
)

@Serializable
data class TVShow(
    val id: String,
    val title: String,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val rating: Float,
    val releaseYear: String,
    val description: String,
    val genres: List<String>,
    val status: String, // "Ongoing", "Ended", "Cancelled"
    val episodeProgress: String? = null, // "S1E5"
    val totalSeasons: Int? = null,
    val totalEpisodes: Int? = null,
    val seasons: List<Season> = emptyList(),
    val cast: List<CastMember> = emptyList(),
    val crew: List<CrewMember> = emptyList(),
    val isWatched: Boolean = false,
    val isInWatchlist: Boolean = false,
    val userRating: Float? = null,
    val userReview: String? = null,
    val watchedDate: String? = null
)

@Serializable
data class Season(
    val id: String,
    val seasonNumber: Int,
    val title: String? = null,
    val episodeCount: Int,
    val airDate: String? = null,
    val episodes: List<Episode> = emptyList()
)

@Serializable
data class Episode(
    val id: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val title: String,
    val description: String? = null,
    val airDate: String? = null,
    val runtime: Int? = null, // in minutes
    val isWatched: Boolean = false,
    val watchedDate: String? = null,
    val userRating: Float? = null
)

@Serializable
data class CastMember(
    val id: String,
    val name: String,
    val character: String,
    val profileUrl: String? = null,
    val order: Int = 0
)

@Serializable
data class CrewMember(
    val id: String,
    val name: String,
    val job: String,
    val department: String,
    val profileUrl: String? = null
)

@Serializable
data class CustomList(
    val id: String,
    val name: String,
    val description: String? = null,
    val isPublic: Boolean = false,
    val createdDate: String,
    val updatedDate: String,
    val contentIds: List<String> = emptyList(),
    val contentCount: Int = 0
)

@Serializable
data class WatchlistItem(
    val id: String,
    val contentId: String,
    val contentType: ContentType,
    val addedDate: String
)

@Serializable
data class WatchedItem(
    val id: String,
    val contentId: String,
    val contentType: ContentType,
    val watchedDate: String,
    val userRating: Float? = null,
    val userReview: String? = null
)

@Serializable
data class UserPreferences(
    val favoriteGenres: List<String> = emptyList(),
    val dislikedGenres: List<String> = emptyList(),
    val preferredLanguages: List<String> = listOf("en"),
    val adultContent: Boolean = false,
    val notificationsEnabled: Boolean = true
)

enum class ContentType {
    MOVIE,
    TV_SHOW
}

enum class WatchStatus {
    NOT_WATCHED,
    WATCHING,
    WATCHED,
    DROPPED
}
