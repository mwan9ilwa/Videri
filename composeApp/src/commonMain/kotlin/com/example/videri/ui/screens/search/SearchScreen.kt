package com.example.videri.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun SearchScreen(
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    modifier: Modifier = Modifier
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
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Search Header
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search Field
            SearchField(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { /* TODO: Implement search */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
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
        }
        
        // Search Results
        when {
            searchQuery.isBlank() -> {
                // Show popular/trending content when no search query
                PopularContent(
                    selectedTab = selectedTab,
                    onMovieClick = onMovieClick,
                    onTVShowClick = onTVShowClick,
                    modifier = Modifier.weight(1f)
                )
            }
            else -> {
                // Show search results
                SearchResults(
                    query = searchQuery,
                    selectedTab = selectedTab,
                    movies = searchResults,
                    tvShows = tvSearchResults,
                    onMovieClick = onMovieClick,
                    onTVShowClick = onTVShowClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SearchResults(
    query: String,
    selectedTab: Int,
    movies: List<Movie>,
    tvShows: List<TVShow>,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (selectedTab) {
            0 -> { // All
                if (movies.isNotEmpty()) {
                    item {
                        Text(
                            text = "Movies",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    items(movies) { movie ->
                        ContentCard(
                            title = movie.title,
                            description = movie.description,
                            imageUrl = movie.posterUrl,
                            rating = movie.rating,
                            year = movie.releaseYear,
                            genres = movie.genres,
                            onClick = { onMovieClick(movie.id) },
                            isWatched = movie.isWatched,
                            isInWatchlist = movie.isInWatchlist
                        )
                    }
                }
                
                if (tvShows.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "TV Shows",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    items(tvShows) { show ->
                        ContentCard(
                            title = show.title,
                            description = show.description,
                            imageUrl = show.posterUrl,
                            rating = show.rating,
                            year = show.releaseYear,
                            genres = show.genres,
                            onClick = { onTVShowClick(show.id) },
                            isWatched = show.isWatched,
                            isInWatchlist = show.isInWatchlist
                        )
                    }
                }
            }
            1 -> { // Movies
                items(movies) { movie ->
                    ContentCard(
                        title = movie.title,
                        description = movie.description,
                        imageUrl = movie.posterUrl,
                        rating = movie.rating,
                        year = movie.releaseYear,
                        genres = movie.genres,
                        onClick = { onMovieClick(movie.id) },
                        isWatched = movie.isWatched,
                        isInWatchlist = movie.isInWatchlist
                    )
                }
            }
            2 -> { // TV Shows
                items(tvShows) { show ->
                    ContentCard(
                        title = show.title,
                        description = show.description,
                        imageUrl = show.posterUrl,
                        rating = show.rating,
                        year = show.releaseYear,
                        genres = show.genres,
                        onClick = { onTVShowClick(show.id) },
                        isWatched = show.isWatched,
                        isInWatchlist = show.isInWatchlist
                    )
                }
            }
        }
        
        // Empty state
        if ((selectedTab == 0 && movies.isEmpty() && tvShows.isEmpty()) ||
            (selectedTab == 1 && movies.isEmpty()) ||
            (selectedTab == 2 && tvShows.isEmpty())) {
            item {
                EmptyState(
                    title = "No results found",
                    description = "Try adjusting your search terms or filters",
                    icon = "🔍"
                )
            }
        }
    }
}

@Composable
private fun PopularContent(
    selectedTab: Int,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val popularMovies = remember {
        listOf(
            Movie(
                id = "1",
                title = "The Shawshank Redemption",
                posterUrl = null,
                rating = 9.3f,
                releaseYear = "1994",
                description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                genres = listOf("Drama")
            ),
            Movie(
                id = "2",
                title = "The Godfather",
                posterUrl = null,
                rating = 9.2f,
                releaseYear = "1972",
                description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                genres = listOf("Crime", "Drama")
            ),
            Movie(
                id = "3",
                title = "Pulp Fiction",
                posterUrl = null,
                rating = 8.9f,
                releaseYear = "1994",
                description = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                genres = listOf("Crime", "Drama")
            )
        )
    }
    
    val popularTVShows = remember {
        listOf(
            TVShow(
                id = "1",
                title = "Game of Thrones",
                posterUrl = null,
                rating = 9.2f,
                releaseYear = "2011",
                description = "Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia.",
                genres = listOf("Action", "Adventure", "Drama"),
                status = "Ended"
            ),
            TVShow(
                id = "2",
                title = "The Sopranos",
                posterUrl = null,
                rating = 9.2f,
                releaseYear = "1999",
                description = "New Jersey mob boss Tony Soprano deals with personal and professional issues in his home and business life.",
                genres = listOf("Crime", "Drama"),
                status = "Ended"
            )
        )
    }
    
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        when (selectedTab) {
            0 -> { // All
                SectionHeader(
                    title = "Popular Movies",
                    subtitle = "Highest rated movies of all time"
                )
                
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    popularMovies.forEach { movie ->
                        ContentCard(
                            title = movie.title,
                            description = movie.description,
                            imageUrl = movie.posterUrl,
                            rating = movie.rating,
                            year = movie.releaseYear,
                            genres = movie.genres,
                            onClick = { onMovieClick(movie.id) },
                            isWatched = movie.isWatched,
                            isInWatchlist = movie.isInWatchlist
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                SectionHeader(
                    title = "Popular TV Shows",
                    subtitle = "Highest rated TV shows of all time"
                )
                
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    popularTVShows.forEach { show ->
                        ContentCard(
                            title = show.title,
                            description = show.description,
                            imageUrl = show.posterUrl,
                            rating = show.rating,
                            year = show.releaseYear,
                            genres = show.genres,
                            onClick = { onTVShowClick(show.id) },
                            isWatched = show.isWatched,
                            isInWatchlist = show.isInWatchlist
                        )
                    }
                }
            }
            1 -> { // Movies
                SectionHeader(
                    title = "Popular Movies",
                    subtitle = "Highest rated movies of all time"
                )
                
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    popularMovies.forEach { movie ->
                        ContentCard(
                            title = movie.title,
                            description = movie.description,
                            imageUrl = movie.posterUrl,
                            rating = movie.rating,
                            year = movie.releaseYear,
                            genres = movie.genres,
                            onClick = { onMovieClick(movie.id) },
                            isWatched = movie.isWatched,
                            isInWatchlist = movie.isInWatchlist
                        )
                    }
                }
            }
            2 -> { // TV Shows
                SectionHeader(
                    title = "Popular TV Shows",
                    subtitle = "Highest rated TV shows of all time"
                )
                
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    popularTVShows.forEach { show ->
                        ContentCard(
                            title = show.title,
                            description = show.description,
                            imageUrl = show.posterUrl,
                            rating = show.rating,
                            year = show.releaseYear,
                            genres = show.genres,
                            onClick = { onTVShowClick(show.id) },
                            isWatched = show.isWatched,
                            isInWatchlist = show.isInWatchlist
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
