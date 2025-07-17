package com.example.videri.ui.screens.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.videri.ui.components.*
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.screens.home.TVShow

@Composable
fun LibraryScreen(
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    onOpenProfile: () -> Unit,
    onNavigateToCustomLists: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var showSearchOverlay by remember { mutableStateOf(false) }
    val tabs = listOf("Watchlist", "Watched", "Lists")
    
    // Mock data
    val watchlistMovies = remember {
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
                title = "Oppenheimer",
                posterUrl = null,
                rating = 8.3f,
                releaseYear = "2023",
                description = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
                genres = listOf("Biography", "Drama", "History"),
                isInWatchlist = true
            )
        )
    }
    
    val watchedMovies = remember {
        listOf(
            Movie(
                id = "3",
                title = "The Batman",
                posterUrl = null,
                rating = 7.8f,
                releaseYear = "2022",
                description = "When a sadistic serial killer begins murdering key political figures in Gotham, Batman is forced to investigate the city's hidden corruption.",
                genres = listOf("Action", "Crime", "Drama"),
                isWatched = true
            ),
            Movie(
                id = "4",
                title = "Spider-Man: No Way Home",
                posterUrl = null,
                rating = 8.2f,
                releaseYear = "2021",
                description = "With Spider-Man's identity now revealed, Peter asks Doctor Strange for help.",
                genres = listOf("Action", "Adventure", "Fantasy"),
                isWatched = true
            )
        )
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        AppHeader(
            title = "Your Library",
            onSearchClick = { showSearchOverlay = true },
            onProfileClick = onOpenProfile
        )
        
        // Content
        when (selectedTab) {
            0 -> { // Watchlist
                if (watchlistMovies.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(watchlistMovies) { movie ->
                            ContentCard(
                                title = movie.title,
                                description = movie.description,
                                posterUrl = movie.posterUrl,
                                rating = movie.rating,
                                releaseYear = movie.releaseYear,
                                genres = movie.genres,
                                onClick = { onMovieClick(movie.id) },
                                isWatched = movie.isWatched,
                                isInWatchlist = movie.isInWatchlist,
                                type = "movie"
                            )
                        }
                    }
                } else {
                    EmptyState(
                        title = "Your watchlist is empty",
                        description = "Add movies and TV shows you want to watch later",
                        icon = "📌",
                        action = {
                            VideriButton(
                                onClick = { /* TODO: Navigate to search */ },
                                variant = ButtonVariant.Primary
                            ) {
                                Text("Browse Content")
                            }
                        }
                    )
                }
            }
            1 -> { // Watched
                if (watchedMovies.isNotEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(watchedMovies) { movie ->
                            ContentCard(
                                title = movie.title,
                                description = movie.description,
                                posterUrl = movie.posterUrl,
                                rating = movie.rating,
                                releaseYear = movie.releaseYear,
                                genres = movie.genres,
                                onClick = { onMovieClick(movie.id) },
                                isWatched = movie.isWatched,
                                isInWatchlist = movie.isInWatchlist,
                                type = "movie"
                            )
                        }
                    }
                } else {
                    EmptyState(
                        title = "No watched content",
                        description = "Movies and TV shows you've watched will appear here",
                        icon = "✅",
                        action = {
                            VideriButton(
                                onClick = { /* TODO: Navigate to search */ },
                                variant = ButtonVariant.Primary
                            ) {
                                Text("Discover Content")
                            }
                        }
                    )
                }
            }
            2 -> { // Lists
                EmptyState(
                    title = "No custom lists",
                    description = "Create custom lists to organize your favorite content",
                    icon = "📝",
                    action = {
                        VideriButton(
                            onClick = onNavigateToCustomLists,
                            variant = ButtonVariant.Primary
                        ) {
                            Text("Create List")
                        }
                    }
                )
            }
        }
    }
    
    // Search Overlay
    SearchOverlay(
        isVisible = showSearchOverlay,
        onDismiss = { showSearchOverlay = false },
        onMovieClick = onMovieClick,
        onTVShowClick = onTVShowClick
    )
}
