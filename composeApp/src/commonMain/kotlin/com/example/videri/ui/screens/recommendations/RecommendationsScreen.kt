package com.example.videri.ui.screens.recommendations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.videri.ui.components.*
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.screens.home.TVShow
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons

data class RecommendationCategory(
    val title: String,
    val description: String,
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TVShow> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen(
    onBackClick: () -> Unit,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    onRefreshRecommendations: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(false) }
    
    // Mock recommendation data based on user's watching history
    val recommendations = remember {
        listOf(
            RecommendationCategory(
                title = "Because you watched The Dark Knight",
                description = "More superhero and crime films",
                movies = listOf(
                    Movie(
                        id = "1",
                        title = "Batman Begins",
                        posterUrl = null,
                        rating = 8.2f,
                        releaseYear = "2005",
                        description = "Bruce Wayne begins his fight against crime as Batman.",
                        genres = listOf("Action", "Crime", "Drama")
                    ),
                    Movie(
                        id = "2",
                        title = "The Dark Knight Rises",
                        posterUrl = null,
                        rating = 8.4f,
                        releaseYear = "2012",
                        description = "Batman faces his most dangerous adversary yet.",
                        genres = listOf("Action", "Crime", "Drama")
                    )
                )
            ),
            RecommendationCategory(
                title = "Trending in Sci-Fi",
                description = "Popular science fiction content",
                movies = listOf(
                    Movie(
                        id = "3",
                        title = "Dune",
                        posterUrl = null,
                        rating = 8.0f,
                        releaseYear = "2021",
                        description = "A mythic and emotionally charged hero's journey.",
                        genres = listOf("Adventure", "Drama", "Sci-Fi")
                    )
                ),
                tvShows = listOf(
                    TVShow(
                        id = "1",
                        title = "Foundation",
                        posterUrl = null,
                        rating = 7.3f,
                        releaseYear = "2021",
                        description = "A complex saga of humans scattered on planets throughout the galaxy.",
                        genres = listOf("Drama", "Sci-Fi"),
                        status = "Returning"
                    )
                )
            ),
            RecommendationCategory(
                title = "Similar to Breaking Bad",
                description = "Crime dramas you might enjoy",
                tvShows = listOf(
                    TVShow(
                        id = "2",
                        title = "Better Call Saul",
                        posterUrl = null,
                        rating = 8.8f,
                        releaseYear = "2015",
                        description = "The trials and tribulations of criminal lawyer Jimmy McGill.",
                        genres = listOf("Crime", "Drama"),
                        status = "Ended"
                    ),
                    TVShow(
                        id = "3",
                        title = "Ozark",
                        posterUrl = null,
                        rating = 8.4f,
                        releaseYear = "2017",
                        description = "A financial advisor drags his family into money laundering.",
                        genres = listOf("Crime", "Drama", "Thriller"),
                        status = "Ended"
                    )
                )
            ),
            RecommendationCategory(
                title = "Highly Rated This Year",
                description = "Top-rated content from 2024",
                movies = listOf(
                    Movie(
                        id = "4",
                        title = "Oppenheimer",
                        posterUrl = null,
                        rating = 8.3f,
                        releaseYear = "2023",
                        description = "The story of J. Robert Oppenheimer's role in the development of the atomic bomb.",
                        genres = listOf("Biography", "Drama", "History")
                    )
                )
            )
        )
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    text = "Recommendations",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        isLoading = true
                        onRefreshRecommendations()
                        // Simulate loading
                        // In real app, this would trigger API call
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh recommendations"
                    )
                }
            }
        )
        
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(
                    message = "Finding new recommendations..."
                )
            }
        } else {
            // Recommendations Content
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Header
                item {
                    RecommendationsHeader()
                }
                
                // Recommendation Categories
                items(recommendations) { category ->
                    RecommendationCategorySection(
                        category = category,
                        onMovieClick = onMovieClick,
                        onTVShowClick = onTVShowClick
                    )
                }
                
                // Feedback Section
                item {
                    RecommendationFeedback()
                }
            }
        }
    }
}

@Composable
private fun RecommendationsHeader() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Personalized for You",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Based on your viewing history, ratings, and preferences, we've curated these recommendations just for you.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun RecommendationCategorySection(
    category: RecommendationCategory,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit
) {
    Column {
        // Category Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                
                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            TextButton(
                onClick = { /* TODO: Navigate to category view */ }
            ) {
                Text("See all")
            }
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Content Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Movies
            items(category.movies) { movie ->
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.posterUrl,
                    rating = movie.rating,
                    releaseYear = movie.releaseYear,
                    onClick = { onMovieClick(movie.id) },
                    isWatched = movie.isWatched,
                    isInWatchlist = movie.isInWatchlist
                )
            }
            
            // TV Shows
            items(category.tvShows) { show ->
                TVShowCard(
                    title = show.title,
                    posterUrl = show.posterUrl,
                    rating = show.rating,
                    releaseYear = show.releaseYear,
                    status = show.status,
                    onClick = { onTVShowClick(show.id) },
                    isWatched = show.isWatched,
                    isInWatchlist = show.isInWatchlist
                )
            }
        }
    }
}

@Composable
private fun RecommendationFeedback() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = LineIcons.Star,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Help us improve",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Rate movies and shows to get better recommendations",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { /* TODO: Open feedback form */ },
                    label = { Text("Give Feedback") }
                )
                
                AssistChip(
                    onClick = { /* TODO: Open preferences */ },
                    label = { Text("Preferences") }
                )
            }
        }
    }
}

@Composable
fun RecommendationAlgorithmExplanation() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "How we recommend content",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            val factors = listOf(
                "Your viewing history and ratings" to "Movies and shows you've watched and rated",
                "Genre preferences" to "Based on your most-watched genres",
                "Similar users" to "What people with similar tastes enjoy",
                "Trending content" to "Popular content in your preferred categories",
                "Critical acclaim" to "Highly rated content from critics and users"
            )
            
            factors.forEach { (factor, description) ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = LineIcons.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Column {
                        Text(
                            text = factor,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
