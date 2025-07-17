package com.example.videri.data.mappers

import com.example.videri.data.models.*
import com.example.videri.data.network.models.*
import com.example.videri.data.network.NetworkClient

object TMDBMapper {
    
    fun mapMovie(tmdbMovie: TMDBMovie, genres: Map<Int, String> = emptyMap()): Movie {
        return Movie(
            id = tmdbMovie.id.toString(),
            title = tmdbMovie.title,
            posterUrl = getImageUrl(tmdbMovie.posterPath),
            backdropUrl = getImageUrl(tmdbMovie.backdropPath, NetworkClient.Config.BACKDROP_SIZE_W780),
            rating = tmdbMovie.voteAverage.toFloat(),
            releaseYear = tmdbMovie.releaseDate.take(4), // Extract year from "YYYY-MM-DD"
            description = tmdbMovie.overview,
            genres = tmdbMovie.genreIds.mapNotNull { genres[it] }
        )
    }
    
    fun mapMovieDetails(tmdbDetails: TMDBMovieDetails, credits: TMDBCredits? = null): Movie {
        return Movie(
            id = tmdbDetails.id.toString(),
            title = tmdbDetails.title,
            posterUrl = getImageUrl(tmdbDetails.posterPath),
            backdropUrl = getImageUrl(tmdbDetails.backdropPath, NetworkClient.Config.BACKDROP_SIZE_W780),
            rating = tmdbDetails.voteAverage.toFloat(),
            releaseYear = tmdbDetails.releaseDate.take(4),
            description = tmdbDetails.overview,
            genres = tmdbDetails.genres.map { it.name },
            runtime = tmdbDetails.runtime,
            budget = tmdbDetails.budget,
            boxOffice = tmdbDetails.revenue,
            cast = credits?.cast?.map { mapCastMember(it) } ?: emptyList(),
            crew = credits?.crew?.map { mapCrewMember(it) } ?: emptyList()
        )
    }
    
    fun mapTVShow(tmdbShow: TMDBTVShow, genres: Map<Int, String> = emptyMap()): TVShow {
        return TVShow(
            id = tmdbShow.id.toString(),
            title = tmdbShow.name,
            posterUrl = getImageUrl(tmdbShow.posterPath),
            backdropUrl = getImageUrl(tmdbShow.backdropPath, NetworkClient.Config.BACKDROP_SIZE_W780),
            rating = tmdbShow.voteAverage.toFloat(),
            releaseYear = tmdbShow.firstAirDate.take(4),
            description = tmdbShow.overview,
            genres = tmdbShow.genreIds.mapNotNull { genres[it] },
            status = "Unknown" // Basic mapping doesn't have status info
        )
    }
    
    fun mapTVShowDetails(tmdbDetails: TMDBTVDetails, credits: TMDBCredits? = null): TVShow {
        return TVShow(
            id = tmdbDetails.id.toString(),
            title = tmdbDetails.name,
            posterUrl = getImageUrl(tmdbDetails.posterPath),
            backdropUrl = getImageUrl(tmdbDetails.backdropPath, NetworkClient.Config.BACKDROP_SIZE_W780),
            rating = tmdbDetails.voteAverage.toFloat(),
            releaseYear = tmdbDetails.firstAirDate.take(4),
            description = tmdbDetails.overview,
            genres = tmdbDetails.genres.map { it.name },
            status = mapTVStatus(tmdbDetails.status),
            totalSeasons = tmdbDetails.numberOfSeasons,
            totalEpisodes = tmdbDetails.numberOfEpisodes,
            seasons = tmdbDetails.seasons.map { mapSeason(it) },
            cast = credits?.cast?.map { mapCastMember(it) } ?: emptyList(),
            crew = credits?.crew?.map { mapCrewMember(it) } ?: emptyList()
        )
    }
    
    private fun mapCastMember(tmdbCast: TMDBCastMember): CastMember {
        return CastMember(
            id = tmdbCast.id.toString(),
            name = tmdbCast.name,
            character = tmdbCast.character,
            profileUrl = getImageUrl(tmdbCast.profilePath, NetworkClient.Config.PROFILE_SIZE_W185),
            order = tmdbCast.order
        )
    }
    
    private fun mapCrewMember(tmdbCrew: TMDBCrewMember): CrewMember {
        return CrewMember(
            id = tmdbCrew.id.toString(),
            name = tmdbCrew.name,
            job = tmdbCrew.job,
            department = tmdbCrew.department,
            profileUrl = getImageUrl(tmdbCrew.profilePath, NetworkClient.Config.PROFILE_SIZE_W185)
        )
    }
    
    private fun mapSeason(tmdbSeason: TMDBSeason): Season {
        return Season(
            id = tmdbSeason.id.toString(),
            seasonNumber = tmdbSeason.seasonNumber,
            title = tmdbSeason.name,
            episodeCount = tmdbSeason.episodeCount,
            airDate = tmdbSeason.airDate
        )
    }
    
    private fun mapTVStatus(tmdbStatus: String): String {
        return when (tmdbStatus.lowercase()) {
            "returning series", "in production" -> "Ongoing"
            "ended", "canceled" -> "Ended"
            "pilot" -> "Pilot"
            else -> "Unknown"
        }
    }
    
    private fun getImageUrl(path: String?, size: String = NetworkClient.Config.POSTER_SIZE_W342): String? {
        return if (path != null) {
            "${NetworkClient.Config.TMDB_IMAGE_BASE_URL}/$size$path"
        } else null
    }
    
    // Genre mapping - This should be loaded from TMDB API or cached
    val movieGenres = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )
    
    val tvGenres = mapOf(
        10759 to "Action & Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        10762 to "Kids",
        9648 to "Mystery",
        10763 to "News",
        10764 to "Reality",
        10765 to "Sci-Fi & Fantasy",
        10766 to "Soap",
        10767 to "Talk",
        10768 to "War & Politics",
        37 to "Western"
    )
}
