package com.example.videri.data.repositories.impl

import com.example.videri.data.models.*
import com.example.videri.data.repositories.ContentRepository
import kotlinx.coroutines.delay

class MockContentRepository : ContentRepository {
    
    private val mockMovies = listOf(
        Movie(
            id = "1",
            title = "The Dark Knight",
            rating = 9.0f,
            releaseYear = "2008",
            description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            genres = listOf("Action", "Crime", "Drama"),
            runtime = 152,
            budget = 185_000_000,
            boxOffice = 1_005_000_000,
            cast = listOf(
                CastMember("1", "Christian Bale", "Bruce Wayne / Batman", order = 0),
                CastMember("2", "Heath Ledger", "Joker", order = 1),
                CastMember("3", "Aaron Eckhart", "Harvey Dent", order = 2),
                CastMember("4", "Maggie Gyllenhaal", "Rachel Dawes", order = 3),
                CastMember("5", "Gary Oldman", "Commissioner Gordon", order = 4)
            ),
            crew = listOf(
                CrewMember("1", "Christopher Nolan", "Director", "Directing"),
                CrewMember("2", "David S. Goyer", "Writer", "Writing"),
                CrewMember("3", "Emma Thomas", "Producer", "Production"),
                CrewMember("4", "Wally Pfister", "Cinematographer", "Camera"),
                CrewMember("5", "Hans Zimmer", "Music", "Sound")
            )
        ),
        Movie(
            id = "2",
            title = "Inception",
            rating = 8.8f,
            releaseYear = "2010",
            description = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            genres = listOf("Action", "Sci-Fi", "Thriller"),
            runtime = 148,
            budget = 160_000_000,
            boxOffice = 836_800_000,
            cast = listOf(
                CastMember("6", "Leonardo DiCaprio", "Dom Cobb", order = 0),
                CastMember("7", "Marion Cotillard", "Mal", order = 1),
                CastMember("8", "Tom Hardy", "Eames", order = 2)
            ),
            crew = listOf(
                CrewMember("1", "Christopher Nolan", "Director", "Directing"),
                CrewMember("6", "Christopher Nolan", "Writer", "Writing"),
                CrewMember("7", "Emma Thomas", "Producer", "Production")
            )
        ),
        Movie(
            id = "3",
            title = "Interstellar",
            rating = 8.6f,
            releaseYear = "2014",
            description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            genres = listOf("Adventure", "Drama", "Sci-Fi"),
            runtime = 169,
            budget = 165_000_000,
            boxOffice = 677_500_000,
            cast = listOf(
                CastMember("9", "Matthew McConaughey", "Cooper", order = 0),
                CastMember("10", "Anne Hathaway", "Brand", order = 1),
                CastMember("11", "Jessica Chastain", "Murph", order = 2)
            ),
            crew = listOf(
                CrewMember("1", "Christopher Nolan", "Director", "Directing"),
                CrewMember("8", "Jonathan Nolan", "Writer", "Writing")
            )
        )
    )
    
    private val mockTVShows = listOf(
        TVShow(
            id = "1",
            title = "The Last of Us",
            rating = 8.7f,
            releaseYear = "2023",
            description = "After a global pandemic destroys civilization, a hardened survivor takes charge of a 14-year-old girl who may be humanity's last hope.",
            genres = listOf("Drama", "Horror", "Thriller"),
            status = "Ended",
            episodeProgress = "S1E9",
            totalSeasons = 1,
            totalEpisodes = 9,
            seasons = listOf(
                Season(
                    id = "s1",
                    seasonNumber = 1,
                    title = "Season 1",
                    episodeCount = 9,
                    airDate = "2023-01-15",
                    episodes = generateEpisodes(1, 9)
                )
            ),
            cast = listOf(
                CastMember("12", "Pedro Pascal", "Joel Miller", order = 0),
                CastMember("13", "Bella Ramsey", "Ellie Williams", order = 1),
                CastMember("14", "Anna Torv", "Tess Servopoulos", order = 2)
            ),
            crew = listOf(
                CrewMember("9", "Craig Mazin", "Creator", "Writing"),
                CrewMember("10", "Neil Druckmann", "Creator", "Writing")
            )
        ),
        TVShow(
            id = "2",
            title = "Breaking Bad",
            rating = 9.5f,
            releaseYear = "2008",
            description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.",
            genres = listOf("Crime", "Drama", "Thriller"),
            status = "Ended",
            totalSeasons = 5,
            totalEpisodes = 62,
            seasons = listOf(
                Season(
                    id = "bb_s1",
                    seasonNumber = 1,
                    title = "Season 1",
                    episodeCount = 7,
                    airDate = "2008-01-20",
                    episodes = generateEpisodes(1, 7)
                ),
                Season(
                    id = "bb_s2",
                    seasonNumber = 2,
                    title = "Season 2",
                    episodeCount = 13,
                    airDate = "2009-03-08",
                    episodes = generateEpisodes(2, 13)
                )
            ),
            cast = listOf(
                CastMember("15", "Bryan Cranston", "Walter White", order = 0),
                CastMember("16", "Aaron Paul", "Jesse Pinkman", order = 1),
                CastMember("17", "Anna Gunn", "Skyler White", order = 2)
            ),
            crew = listOf(
                CrewMember("11", "Vince Gilligan", "Creator", "Writing")
            )
        )
    )
    
    private fun generateEpisodes(seasonNumber: Int, count: Int): List<Episode> {
        return (1..count).map { episodeNumber ->
            Episode(
                id = "s${seasonNumber}e${episodeNumber}",
                episodeNumber = episodeNumber,
                seasonNumber = seasonNumber,
                title = "Episode $episodeNumber",
                description = "Episode description for Season $seasonNumber, Episode $episodeNumber",
                runtime = 45,
                isWatched = false
            )
        }
    }
    
    override suspend fun getMovie(id: String): Movie? {
        delay(500) // Simulate network delay
        return mockMovies.find { it.id == id }
    }
    
    override suspend fun getTVShow(id: String): TVShow? {
        delay(500) // Simulate network delay
        return mockTVShows.find { it.id == id }
    }
    
    override suspend fun getPopularMovies(page: Int): List<Movie> {
        delay(1000) // Simulate network delay
        return mockMovies.shuffled().take(10)
    }
    
    override suspend fun getPopularTVShows(page: Int): List<TVShow> {
        delay(1000) // Simulate network delay
        return mockTVShows.shuffled().take(10)
    }
    
    override suspend fun getTrendingMovies(): List<Movie> {
        delay(800) // Simulate network delay
        return mockMovies.sortedByDescending { it.rating }.take(5)
    }
    
    override suspend fun getTrendingTVShows(): List<TVShow> {
        delay(800) // Simulate network delay
        return mockTVShows.sortedByDescending { it.rating }.take(5)
    }
    
    override suspend fun getTopRatedMovies(): List<Movie> {
        delay(800) // Simulate network delay
        return mockMovies.sortedByDescending { it.rating }
    }
    
    override suspend fun getTopRatedTVShows(): List<TVShow> {
        delay(800) // Simulate network delay
        return mockTVShows.sortedByDescending { it.rating }
    }
    
    override suspend fun searchMovies(query: String): List<Movie> {
        delay(500) // Simulate network delay
        return mockMovies.filter { 
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.genres.any { genre -> genre.contains(query, ignoreCase = true) }
        }
    }
    
    override suspend fun searchTVShows(query: String): List<TVShow> {
        delay(500) // Simulate network delay
        return mockTVShows.filter { 
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true) ||
            it.genres.any { genre -> genre.contains(query, ignoreCase = true) }
        }
    }
    
    override suspend fun getMoviesByGenre(genre: String): List<Movie> {
        delay(600) // Simulate network delay
        return mockMovies.filter { movie ->
            movie.genres.any { it.equals(genre, ignoreCase = true) }
        }
    }
    
    override suspend fun getTVShowsByGenre(genre: String): List<TVShow> {
        delay(600) // Simulate network delay
        return mockTVShows.filter { show ->
            show.genres.any { it.equals(genre, ignoreCase = true) }
        }
    }
    
    override suspend fun getRecommendations(contentType: ContentType, contentId: String): List<Any> {
        delay(1000) // Simulate network delay
        return when (contentType) {
            ContentType.MOVIE -> mockMovies.filter { it.id != contentId }.take(5)
            ContentType.TV_SHOW -> mockTVShows.filter { it.id != contentId }.take(5)
        }
    }
    
    override suspend fun getSimilarContent(contentType: ContentType, contentId: String): List<Any> {
        delay(800) // Simulate network delay
        return when (contentType) {
            ContentType.MOVIE -> {
                val movie = mockMovies.find { it.id == contentId }
                if (movie != null) {
                    mockMovies.filter { 
                        it.id != contentId && 
                        it.genres.any { genre -> movie.genres.contains(genre) }
                    }.take(3)
                } else emptyList()
            }
            ContentType.TV_SHOW -> {
                val show = mockTVShows.find { it.id == contentId }
                if (show != null) {
                    mockTVShows.filter { 
                        it.id != contentId && 
                        it.genres.any { genre -> show.genres.contains(genre) }
                    }.take(3)
                } else emptyList()
            }
        }
    }
}
