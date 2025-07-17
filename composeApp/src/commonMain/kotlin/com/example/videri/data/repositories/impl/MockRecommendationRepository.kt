package com.example.videri.data.repositories.impl

import com.example.videri.data.models.*
import com.example.videri.data.repositories.RecommendationRepository
import kotlinx.coroutines.delay

class MockRecommendationRepository : RecommendationRepository {
    
    private val mockRecommendedMovies = listOf(
        Movie(
            id = "rec_1",
            title = "Dune",
            rating = 8.0f,
            releaseYear = "2021",
            description = "A noble family becomes embroiled in a war for control over the galaxy's most valuable asset.",
            genres = listOf("Action", "Adventure", "Drama", "Sci-Fi")
        ),
        Movie(
            id = "rec_2",
            title = "The Matrix",
            rating = 8.7f,
            releaseYear = "1999",
            description = "A computer hacker learns from mysterious rebels about the true nature of his reality.",
            genres = listOf("Action", "Sci-Fi")
        ),
        Movie(
            id = "rec_3",
            title = "Blade Runner 2049",
            rating = 8.0f,
            releaseYear = "2017",
            description = "A young blade runner's discovery of a long-buried secret leads him to track down former blade runner Rick Deckard.",
            genres = listOf("Action", "Drama", "Mystery", "Sci-Fi", "Thriller")
        ),
        Movie(
            id = "rec_4",
            title = "Parasite",
            rating = 8.6f,
            releaseYear = "2019",
            description = "A poor family schemes to become employed by a wealthy family by infiltrating their household and posing as unrelated, highly qualified individuals.",
            genres = listOf("Comedy", "Drama", "Thriller")
        ),
        Movie(
            id = "rec_5",
            title = "Mad Max: Fury Road",
            rating = 8.1f,
            releaseYear = "2015",
            description = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland.",
            genres = listOf("Action", "Adventure", "Sci-Fi", "Thriller")
        )
    )
    
    private val mockRecommendedTVShows = listOf(
        TVShow(
            id = "rec_tv_1",
            title = "Stranger Things",
            rating = 8.7f,
            releaseYear = "2016",
            description = "When a young boy disappears, his mother, a police chief and his friends must confront terrifying supernatural forces.",
            genres = listOf("Drama", "Fantasy", "Horror", "Mystery", "Sci-Fi", "Thriller"),
            status = "Ended",
            totalSeasons = 4,
            totalEpisodes = 42
        ),
        TVShow(
            id = "rec_tv_2",
            title = "The Bear",
            rating = 8.7f,
            releaseYear = "2022",
            description = "A young chef from the fine dining world comes home to Chicago to run his family sandwich shop.",
            genres = listOf("Comedy", "Drama"),
            status = "Ongoing",
            totalSeasons = 3,
            totalEpisodes = 28
        ),
        TVShow(
            id = "rec_tv_3",
            title = "House of the Dragon",
            rating = 8.5f,
            releaseYear = "2022",
            description = "An internal succession war within House Targaryen at the height of its power, 172 years before the birth of Daenerys Targaryen.",
            genres = listOf("Action", "Adventure", "Drama", "Fantasy"),
            status = "Ongoing",
            totalSeasons = 2,
            totalEpisodes = 18
        ),
        TVShow(
            id = "rec_tv_4",
            title = "Wednesday",
            rating = 8.1f,
            releaseYear = "2022",
            description = "Follows Wednesday Addams' years as a student at Nevermore Academy.",
            genres = listOf("Comedy", "Crime", "Family", "Horror", "Mystery"),
            status = "Ongoing",
            totalSeasons = 1,
            totalEpisodes = 8
        ),
        TVShow(
            id = "rec_tv_5",
            title = "The White Lotus",
            rating = 7.6f,
            releaseYear = "2021",
            description = "The exploits of various guests and employees at an exclusive tropical resort.",
            genres = listOf("Comedy", "Drama", "Mystery", "Thriller"),
            status = "Ongoing",
            totalSeasons = 2,
            totalEpisodes = 13
        )
    )
    
    private fun getGenreBasedRecommendations(targetGenres: List<String>): List<Any> {
        val movies = mockRecommendedMovies.filter { movie ->
            movie.genres.any { genre -> targetGenres.any { it.equals(genre, ignoreCase = true) } }
        }
        val shows = mockRecommendedTVShows.filter { show ->
            show.genres.any { genre -> targetGenres.any { it.equals(genre, ignoreCase = true) } }
        }
        return (movies + shows).shuffled().take(8)
    }
    
    override suspend fun getPersonalizedRecommendations(): List<Any> {
        delay(1200) // Simulate complex recommendation algorithm processing
        
        // In a real implementation, this would analyze user's watch history, ratings, and preferences
        // For now, return a mix of popular content
        return (mockRecommendedMovies.take(3) + mockRecommendedTVShows.take(3)).shuffled()
    }
    
    override suspend fun getRecommendationsByGenre(genre: String): List<Any> {
        delay(800)
        return getGenreBasedRecommendations(listOf(genre))
    }
    
    override suspend fun getRecommendationsByMood(mood: String): List<Any> {
        delay(1000)
        
        val moodGenreMap = mapOf(
            "action" to listOf("Action", "Adventure", "Thriller"),
            "comedy" to listOf("Comedy", "Family"),
            "drama" to listOf("Drama", "Romance"),
            "thriller" to listOf("Thriller", "Mystery", "Crime"),
            "sci-fi" to listOf("Sci-Fi", "Fantasy"),
            "horror" to listOf("Horror", "Thriller"),
            "feel-good" to listOf("Comedy", "Family", "Romance"),
            "intense" to listOf("Thriller", "Horror", "Crime", "Action"),
            "thought-provoking" to listOf("Drama", "Sci-Fi", "Mystery")
        )
        
        val genres = moodGenreMap[mood.lowercase()] ?: listOf("Drama")
        return getGenreBasedRecommendations(genres)
    }
    
    override suspend fun getTrendingRecommendations(): List<Any> {
        delay(600)
        
        // Return the highest-rated content as "trending"
        val topMovies = mockRecommendedMovies.sortedByDescending { it.rating }.take(3)
        val topShows = mockRecommendedTVShows.sortedByDescending { it.rating }.take(3)
        
        return (topMovies + topShows).shuffled()
    }
    
    override suspend fun refreshRecommendations() {
        delay(2000) // Simulate refreshing recommendations from server
        // In a real implementation, this would trigger a refresh of recommendation algorithms
    }
}
