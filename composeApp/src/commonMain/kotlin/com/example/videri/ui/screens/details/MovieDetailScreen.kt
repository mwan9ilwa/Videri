package com.example.videri.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.videri.ui.components.*
import com.example.videri.data.models.Movie
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons
import com.example.videri.ui.viewmodels.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: String,
    viewModel: MovieDetailViewModel,
    onBackClick: () -> Unit,
    onAddToList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState
    
    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }
    
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    VideriButton(
                        onClick = { viewModel.loadMovie(movieId) },
                        variant = ButtonVariant.Primary
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
        
        uiState.movie != null -> {
            MovieDetailContent(
                movie = uiState.movie,
                isWatched = uiState.isWatched,
                isInWatchlist = uiState.isInWatchlist,
                userRating = uiState.userRating,
                userReview = uiState.userReview,
                similarMovies = uiState.similarMovies,
                onBackClick = onBackClick,
                onWatchlistToggle = { viewModel.toggleWatchlist() },
                onWatchedToggle = { viewModel.toggleWatched() },
                onRatingChange = { viewModel.updateUserRating(it) },
                onReviewChange = { viewModel.updateUserReview(it) },
                onAddToList = onAddToList,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailContent(
    movie: Movie,
    isWatched: Boolean,
    isInWatchlist: Boolean,
    userRating: Float,
    userReview: String,
    similarMovies: List<Movie>,
    onBackClick: () -> Unit,
    onWatchlistToggle: () -> Unit,
    onWatchedToggle: () -> Unit,
    onRatingChange: (Float) -> Unit,
    onReviewChange: (String) -> Unit,
    onAddToList: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* TODO: More options */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Section
            MovieHeroSection(
                movie = movie,
                runtime = movie.runtime?.let { "${it} minutes" } ?: "Unknown",
                isWatched = isWatched,
                isInWatchlist = isInWatchlist,
                onWatchlistToggle = onWatchlistToggle,
                onWatchedToggle = onWatchedToggle
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // User Rating Section
            UserRatingSection(
                userRating = userRating,
                userReview = userReview,
                onRatingChange = onRatingChange,
                onReviewChange = onReviewChange
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Synopsis Section
            SynopsisSection(
                synopsis = movie.description
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Cast Section
            if (movie.cast.isNotEmpty()) {
                CastSection(cast = movie.cast.map { it.name to it.character })
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // Crew Section
            if (movie.crew.isNotEmpty()) {
                CrewSection(crew = movie.crew.map { it.name to it.job })
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // Movie Details Section
            MovieDetailsSection(
                releaseDate = movie.releaseYear,
                runtime = movie.runtime?.let { "${it} minutes" } ?: "Unknown",
                budget = movie.budget?.let { "$${it / 1_000_000}M" } ?: "Unknown",
                boxOffice = movie.boxOffice?.let { "$${it / 1_000_000}M" } ?: "Unknown",
                genres = movie.genres
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Similar Movies Section
            if (similarMovies.isNotEmpty()) {
                SimilarMoviesSection(
                    similarMovies = similarMovies,
                    onMovieClick = { /* TODO: Navigate to movie */ }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // Action Section
            ActionSection(
                onAddToList = onAddToList,
                onShare = { /* TODO: Share functionality */ }
            )
            
            Spacer(modifier = Modifier.height(100.dp)) // Bottom padding
        }
    }
}

@Composable
private fun MovieHeroSection(
    movie: Movie,
    runtime: String,
    isWatched: Boolean,
    isInWatchlist: Boolean,
    onWatchlistToggle: () -> Unit,
    onWatchedToggle: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Poster
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = LineIcons.Movie,
                    contentDescription = "Movie poster",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
            
            // Movie Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${movie.releaseYear} • $runtime",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Rating
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = LineIcons.Star,
                        contentDescription = null,
                        tint = MaterialTheme.extendedColors.rating,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = movie.rating.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.extendedColors.rating,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "/10",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Action Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    VideriButton(
                        onClick = onWatchlistToggle,
                        variant = if (isInWatchlist) ButtonVariant.Secondary else ButtonVariant.Primary,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = LineIcons.Bookmark,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isInWatchlist) "In Watchlist" else "Add to Watchlist",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    
                    VideriButton(
                        onClick = onWatchedToggle,
                        variant = if (isWatched) ButtonVariant.Secondary else ButtonVariant.Primary,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = LineIcons.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isWatched) "Watched" else "Mark as Watched",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UserRatingSection(
    userRating: Float,
    userReview: String,
    onRatingChange: (Float) -> Unit,
    onReviewChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Your Rating & Review",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Star Rating
        StarRating(
            rating = userRating,
            onRatingChanged = onRatingChange,
            interactive = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Review Input
        VideriTextField(
            value = userReview,
            onValueChange = onReviewChange,
            label = "Write a review",
            placeholder = "Share your thoughts about this movie...",
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4,
            singleLine = false
        )
    }
}

@Composable
private fun SynopsisSection(
    synopsis: String
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Synopsis",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = synopsis,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
        )
    }
}

@Composable
private fun CastSection(
    cast: List<Pair<String, String>>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Cast",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        cast.forEach { (actor, character) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = actor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = character,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun CrewSection(
    crew: List<Pair<String, String>>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Crew",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        crew.forEach { (person, role) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = person,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = role,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MovieDetailsSection(
    releaseDate: String,
    runtime: String,
    budget: String,
    boxOffice: String,
    genres: List<String>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        val details = listOf(
            "Release Date" to releaseDate,
            "Runtime" to runtime,
            "Budget" to budget,
            "Box Office" to boxOffice
        )
        
        details.forEach { (label, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Genres
        Text(
            text = "Genres",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            genres.forEach { genre ->
                GenreChip(genre = genre)
            }
        }
    }
}

@Composable
private fun ActionSection(
    onAddToList: () -> Unit,
    onShare: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Actions",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VideriButton(
                onClick = onAddToList,
                variant = ButtonVariant.Secondary,
                modifier = Modifier.weight(1f)
            ) {
                Text("Add to List")
            }
            
            VideriButton(
                onClick = onShare,
                variant = ButtonVariant.Secondary,
                modifier = Modifier.weight(1f)
            ) {
                Text("Share")
            }
        }
    }
}

@Composable
private fun SimilarMoviesSection(
    similarMovies: List<Movie>,
    onMovieClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Similar Movies",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            similarMovies.take(3).forEach { movie ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onMovieClick(movie.id) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = LineIcons.Movie,
                            contentDescription = "Movie poster",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = movie.releaseYear,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
