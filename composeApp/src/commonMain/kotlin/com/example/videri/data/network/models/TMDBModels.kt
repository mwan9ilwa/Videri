package com.example.videri.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TMDBMovieResponse(
    val page: Int,
    val results: List<TMDBMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class TMDBMovie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("genre_ids") val genreIds: List<Int>,
    val adult: Boolean,
    val popularity: Double,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    val video: Boolean
)

@Serializable
data class TMDBMovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    val genres: List<TMDBGenre>,
    val runtime: Int?,
    val budget: Long,
    val revenue: Long,
    val status: String,
    val tagline: String?,
    @SerialName("production_companies") val productionCompanies: List<TMDBProductionCompany>,
    @SerialName("production_countries") val productionCountries: List<TMDBProductionCountry>,
    @SerialName("spoken_languages") val spokenLanguages: List<TMDBSpokenLanguage>
)

@Serializable
data class TMDBTVResponse(
    val page: Int,
    val results: List<TMDBTVShow>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class TMDBTVShow(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    val popularity: Double,
    val adult: Boolean
)

@Serializable
data class TMDBTVDetails(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("last_air_date") val lastAirDate: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    val genres: List<TMDBGenre>,
    val status: String,
    @SerialName("number_of_episodes") val numberOfEpisodes: Int,
    @SerialName("number_of_seasons") val numberOfSeasons: Int,
    val seasons: List<TMDBSeason>,
    @SerialName("created_by") val createdBy: List<TMDBCreator>,
    @SerialName("production_companies") val productionCompanies: List<TMDBProductionCompany>,
    val networks: List<TMDBNetwork>
)

@Serializable
data class TMDBCredits(
    val cast: List<TMDBCastMember>,
    val crew: List<TMDBCrewMember>
)

@Serializable
data class TMDBCastMember(
    val id: Int,
    val name: String,
    val character: String,
    @SerialName("profile_path") val profilePath: String?,
    val order: Int,
    @SerialName("cast_id") val castId: Int?,
    @SerialName("credit_id") val creditId: String
)

@Serializable
data class TMDBCrewMember(
    val id: Int,
    val name: String,
    val job: String,
    val department: String,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("credit_id") val creditId: String
)

@Serializable
data class TMDBGenre(
    val id: Int,
    val name: String
)

@Serializable
data class TMDBProductionCompany(
    val id: Int,
    val name: String,
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class TMDBProductionCountry(
    @SerialName("iso_3166_1") val iso31661: String,
    val name: String
)

@Serializable
data class TMDBSpokenLanguage(
    @SerialName("english_name") val englishName: String,
    @SerialName("iso_639_1") val iso6391: String,
    val name: String
)

@Serializable
data class TMDBSeason(
    val id: Int,
    @SerialName("season_number") val seasonNumber: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("air_date") val airDate: String?,
    @SerialName("episode_count") val episodeCount: Int,
    @SerialName("vote_average") val voteAverage: Double
)

@Serializable
data class TMDBCreator(
    val id: Int,
    val name: String,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("credit_id") val creditId: String
)

@Serializable
data class TMDBNetwork(
    val id: Int,
    val name: String,
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("origin_country") val originCountry: String
)

@Serializable
data class TMDBSearchResponse<T>(
    val page: Int,
    val results: List<T>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
