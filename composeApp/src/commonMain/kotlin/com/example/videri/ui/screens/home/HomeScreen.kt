package com.example.videri.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.videri.ui.components.*

// Mock data models
data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String?,
    val rating: Float,
    val releaseYear: String,
    val description: String,
    val genres: List<String>,
    val isWatched: Boolean = false,
    val isInWatchlist: Boolean = false
)

data class TVShow(
    val id: String,
    val title: String,
    val posterUrl: String?,
    val rating: Float,
    val releaseYear: String,
    val description: String,
    val genres: List<String>,
    val status: String,
    val episodeProgress: String? = null,
    val isWatched: Boolean = false,
    val isInWatchlist: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    onSeeAllClick: (String) -> Unit,
    onOpenProfile: () -> Unit,
    onNavigateToRecommendations: () -> Unit = {},
    modifier: Modifier = Modifier,
    userName: String = "User"
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var showSearchOverlay by remember { mutableStateOf(false) }
    // Mock data
    val trendingMovies = remember {
        listOf(
            Movie(
                id = "1",
                title = "Dune: Part Two",
                posterUrl = null,
                rating = 8.5f,
                releaseYear = "2024",
                description = "Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.",
                genres = listOf("Sci-Fi", "Adventure", "Drama"),
                isInWatchlist = true
            ),
            Movie(
                id = "2",
                title = "The Batman",
                posterUrl = null,
                rating = 7.8f,
                releaseYear = "2022",
                description = "When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption.",
                genres = listOf("Action", "Crime", "Drama"),
                isWatched = true
            ),
            Movie(
                id = "3",
                title = "Everything Everywhere All at Once",
                posterUrl = null,
                rating = 7.8f,
                releaseYear = "2022",
                description = "An aging Chinese immigrant is swept up in an insane adventure, where she alone can save what's important to her.",
                genres = listOf("Comedy", "Drama", "Fantasy")
            )
        )
    }
    
    val trendingTVShows = remember {
        listOf(
            TVShow(
                id = "1",
                title = "The Last of Us",
                posterUrl = null,
                rating = 8.7f,
                releaseYear = "2023",
                description = "After a global pandemic destroys civilization, a hardened survivor takes charge of a 14-year-old girl who may be humanity's last hope.",
                genres = listOf("Drama", "Horror", "Thriller"),
                status = "Ended",
                episodeProgress = "S1E9",
                isWatched = true
            ),
            TVShow(
                id = "2",
                title = "Wednesday",
                posterUrl = null,
                rating = 8.1f,
                releaseYear = "2022",
                description = "Follows Wednesday Addams' years as a student at Nevermore Academy, where she tries to master her emerging psychic ability.",
                genres = listOf("Comedy", "Horror", "Mystery"),
                status = "Returning",
                episodeProgress = "S1E4",
                isInWatchlist = true
            ),
            TVShow(
                id = "3",
                title = "House of the Dragon",
                posterUrl = null,
                rating = 8.4f,
                releaseYear = "2022",
                description = "The story of the Targaryen civil war that took place about 300 years before events portrayed in Game of Thrones.",
                genres = listOf("Action", "Adventure", "Drama"),
                status = "Returning"
            )
        )
    }
    
    val recentlyWatched = remember {
        listOf(
            Movie(
                id = "4",
                title = "Spider-Man: No Way Home",
                posterUrl = null,
                rating = 8.2f,
                releaseYear = "2021",
                description = "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help.",
                genres = listOf("Action", "Adventure", "Fantasy"),
                isWatched = true
            ),
            Movie(
                id = "5",
                title = "Top Gun: Maverick",
                posterUrl = null,
                rating = 8.3f,
                releaseYear = "2022",
                description = "After thirty years, Maverick is still pushing the envelope as a top naval aviator.",
                genres = listOf("Action", "Drama"),
                isWatched = true
            )
        )
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        AppHeader(
            title = "Videri",
            onSearchClick = { showSearchOverlay = true },
            onProfileClick = onOpenProfile
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Continue Watching Section
        if (trendingTVShows.any { it.episodeProgress != null }) {
            SectionHeader(
                title = "Continue Watching",
                subtitle = "Pick up where you left off",
                action = {
                    VideriButton(
                        onClick = { onSeeAllClick("continue") },
                        variant = ButtonVariant.Text
                    ) {
                        Text("See all")
                    }
                }
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(trendingTVShows.filter { it.episodeProgress != null }) { show ->
                    TVShowCard(
                        title = show.title,
                        posterUrl = show.posterUrl,
                        rating = show.rating,
                        releaseYear = show.releaseYear,
                        status = show.status,
                        onClick = { onTVShowClick(show.id) },
                        isWatched = show.isWatched,
                        isInWatchlist = show.isInWatchlist,
                        episodeProgress = show.episodeProgress
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Trending Movies Section
        SectionHeader(
            title = "Trending Movies",
            subtitle = "Popular movies right now",
            action = {
                VideriButton(
                    onClick = { onSeeAllClick("trending_movies") },
                    variant = ButtonVariant.Text
                ) {
                    Text("See all")
                }
            }
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trendingMovies) { movie ->
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
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Trending TV Shows Section
        SectionHeader(
            title = "Trending TV Shows",
            subtitle = "Popular shows this week",
            action = {
                VideriButton(
                    onClick = { onSeeAllClick("trending_tv") },
                    variant = ButtonVariant.Text
                ) {
                    Text("See all")
                }
            }
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(trendingTVShows) { show ->
                TVShowCard(
                    title = show.title,
                    posterUrl = show.posterUrl,
                    rating = show.rating,
                    releaseYear = show.releaseYear,
                    status = show.status,
                    onClick = { onTVShowClick(show.id) },
                    isWatched = show.isWatched,
                    isInWatchlist = show.isInWatchlist,
                    episodeProgress = show.episodeProgress
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Recently Watched Section
        if (recentlyWatched.isNotEmpty()) {
            SectionHeader(
                title = "Recently Watched",
                subtitle = "Your recent activity",
                action = {
                    VideriButton(
                        onClick = { onSeeAllClick("recent") },
                        variant = ButtonVariant.Text
                    ) {
                        Text("See all")
                    }
                }
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recentlyWatched) { movie ->
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
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
        
        // Stats Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Your Stats",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem(
                                label = "Movies Watched",
                                value = "127",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            StatItem(
                                label = "TV Shows",
                                value = "23",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            StatItem(
                                label = "Watchlist",
                                value = "45",
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    
                    VideriButton(
                        onClick = onNavigateToRecommendations,
                        variant = ButtonVariant.Secondary
                    ) {
                        Text(
                            text = "Recommendations",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Overlay
        SearchOverlay(
            isVisible = showSearchOverlay,
            onDismiss = { showSearchOverlay = false },
            onMovieClick = onMovieClick,
            onTVShowClick = onTVShowClick
        )
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = color.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
    }
}
