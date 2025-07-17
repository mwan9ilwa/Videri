package com.example.videri.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.videri.ui.icons.LineIcons
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.screens.home.TVShow
import kotlinx.coroutines.delay

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
    var isSearchActive by remember { mutableStateOf(false) }
    var isSearching by remember { mutableStateOf(false) }
    var showSuggestions by remember { mutableStateOf(false) }
    
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    
    // Mock search history
    val searchHistory = remember {
        listOf(
            "Breaking Bad",
            "Dark Knight",
            "Marvel movies",
            "Horror shows",
            "Sci-fi thriller"
        )
    }
    
    // Filter suggestions based on query
    val filteredSuggestions = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            searchHistory
        } else {
            searchHistory.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }
    
    // Mock search results (same as before)
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
    
    // Simulate search delay
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            isSearching = true
            delay(500) // Simulate search delay
            isSearching = false
        }
    }
    
    // Auto-focus search field when opened
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        isSearchActive = true
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            // Material 3 Search Bar
            Material3SearchBar(
                query = searchQuery,
                onQueryChange = { 
                    searchQuery = it
                    showSuggestions = it.isNotEmpty() || isSearchActive
                },
                onSearch = { query ->
                    if (query.isNotEmpty()) {
                        showSuggestions = false
                        focusManager.clearFocus()
                        // Perform search
                    }
                },
                onActiveChange = { active ->
                    isSearchActive = active
                    showSuggestions = active
                },
                isActive = isSearchActive,
                onBack = {
                    if (isSearchActive) {
                        focusManager.clearFocus()
                        isSearchActive = false
                        showSuggestions = false
                        if (searchQuery.isEmpty()) {
                            onDismiss()
                        }
                    } else {
                        onDismiss()
                    }
                },
                onClose = onDismiss,
                focusRequester = focusRequester,
                isSearching = isSearching
            )
            
            // Search content
            if (showSuggestions && isSearchActive) {
                // Show suggestions
                SearchSuggestions(
                    suggestions = filteredSuggestions,
                    onSuggestionClick = { suggestion ->
                        searchQuery = suggestion
                        showSuggestions = false
                        focusManager.clearFocus()
                        isSearchActive = false
                    }
                )
            } else if (searchQuery.isNotEmpty()) {
                // Show search results
                SearchResultsContent(
                    searchResults = searchResults,
                    tvSearchResults = tvSearchResults,
                    onMovieClick = onMovieClick,
                    onTVShowClick = onTVShowClick,
                    isSearching = isSearching
                )
            }
        }
    }
}

@Composable
private fun Material3SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    isActive: Boolean,
    onBack: () -> Unit,
    onClose: () -> Unit,
    focusRequester: FocusRequester,
    isSearching: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(28.dp),
        shadowElevation = if (isActive) 6.dp else 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading icon (back or search)
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = if (isActive) Icons.Default.ArrowBack else Icons.Default.Search,
                    contentDescription = if (isActive) "Back" else "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Search text field
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        onActiveChange(focusState.isFocused)
                    },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(query)
                    }
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = "Search movies and TV shows",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                    innerTextField()
                }
            )
            
            // Trailing icon (searching indicator or close)
            if (isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchSuggestions(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(suggestions) { suggestion ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClick(suggestion) },
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "Recent search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = suggestion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchResultsContent(
    searchResults: List<Movie>,
    tvSearchResults: List<TVShow>,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    isSearching: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Results header with search indicator
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Results",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                
                if (isSearching) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Searching...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Movie results
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
        
        // TV Show results
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
