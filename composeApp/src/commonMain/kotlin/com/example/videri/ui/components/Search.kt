package com.example.videri.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.videri.ui.icons.LineIcons
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.screens.home.TVShow

@Composable
fun SearchOverlay(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            SearchView(
                onDismiss = onDismiss,
                onMovieClick = onMovieClick,
                onTVShowClick = onTVShowClick
            )
        }
    }
}

@Composable
private fun SearchView(
    onDismiss: () -> Unit,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    
    val tabs = listOf("All", "Movies", "TV Shows")
    val genres = listOf("All", "Action", "Comedy", "Drama", "Horror", "Sci-Fi", "Romance", "Thriller")
    
    // Mock search results
    val searchResults = remember {
        listOf(
            Movie(
                id = "1",
                title = "The Dark Knight",
                posterUrl = null,
                rating = 9.0f,
                releaseYear = "2008",
                description = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                genres = listOf("Action", "Crime", "Drama"),
                isWatched = true
            ),
            Movie(
                id = "2",
                title = "Interstellar",
                posterUrl = null,
                rating = 8.6f,
                releaseYear = "2014",
                description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                genres = listOf("Adventure", "Drama", "Sci-Fi"),
                isInWatchlist = true
            )
        )
    }
    
    val tvSearchResults = remember {
        listOf(
            TVShow(
                id = "1",
                title = "Breaking Bad",
                posterUrl = null,
                rating = 9.5f,
                releaseYear = "2008",
                description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.",
                genres = listOf("Crime", "Drama", "Thriller"),
                status = "Ended",
                isWatched = true
            ),
            TVShow(
                id = "2",
                title = "Stranger Things",
                posterUrl = null,
                rating = 8.7f,
                releaseYear = "2016",
                description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl.",
                genres = listOf("Drama", "Fantasy", "Horror"),
                status = "Ended",
                episodeProgress = "S4E9",
                isInWatchlist = true
            )
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Search Header
            SearchHeader(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onDismiss = onDismiss
            )
            
            // Search Content
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Tab Row
                TabRow(
                    selectedIndex = selectedTab,
                    tabs = tabs,
                    onTabSelected = { selectedTab = it }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Genre Filter
                Text(
                    text = "Genres",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(genres) { genre ->
                        FilterChip(
                            text = genre,
                            selected = selectedGenre == genre || (selectedGenre == null && genre == "All"),
                            onClick = { 
                                selectedGenre = if (genre == "All") null else genre
                            }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Search Results
                SearchResults(
                    selectedTab = selectedTab,
                    searchResults = searchResults,
                    tvSearchResults = tvSearchResults,
                    onMovieClick = onMovieClick,
                    onTVShowClick = onTVShowClick
                )
            }
        }
    }
}

@Composable
private fun SearchHeader(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with close button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search Field
            SearchField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = { /* TODO: Implement search */ }
            )
        }
    }
}

@Composable
private fun SearchResults(
    selectedTab: Int,
    searchResults: List<Movie>,
    tvSearchResults: List<TVShow>,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit
) {
    when (selectedTab) {
        0 -> { // All
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResults) { movie ->
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
                items(tvSearchResults) { show ->
                    ContentCard(
                        title = show.title,
                        description = show.description,
                        posterUrl = show.posterUrl,
                        rating = show.rating,
                        releaseYear = show.releaseYear,
                        genres = show.genres,
                        onClick = { onTVShowClick(show.id) },
                        isWatched = show.isWatched,
                        isInWatchlist = show.isInWatchlist,
                        type = "tv"
                    )
                }
            }
        }
        1 -> { // Movies
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResults) { movie ->
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
        }
        2 -> { // TV Shows
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tvSearchResults) { show ->
                    ContentCard(
                        title = show.title,
                        description = show.description,
                        posterUrl = show.posterUrl,
                        rating = show.rating,
                        releaseYear = show.releaseYear,
                        genres = show.genres,
                        onClick = { onTVShowClick(show.id) },
                        isWatched = show.isWatched,
                        isInWatchlist = show.isInWatchlist,
                        type = "tv"
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surface
        },
        shape = RoundedCornerShape(16.dp),
        shadowElevation = if (selected) 4.dp else 0.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}
