package com.example.videri.ui.screens.details

import androidx.compose.foundation.background
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
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie,
    onBackClick: () -> Unit,
    onWatchlistToggle: (Boolean) -> Unit,
    onWatchedToggle: (Boolean) -> Unit,
    onRatingChange: (Float) -> Unit,
    onAddToList: () -> Unit,
    modifier: Modifier = Modifier
) {
    var userRating by remember { mutableStateOf(0f) }
    var userReview by remember { mutableStateOf("") }
    var isWatched by remember { mutableStateOf(movie.isWatched) }
    var isInWatchlist by remember { mutableStateOf(movie.isInWatchlist) }
    
    // Mock additional data
    val cast = remember {
        listOf(
            "Christian Bale" to "Bruce Wayne / Batman",
            "Heath Ledger" to "Joker",
            "Aaron Eckhart" to "Harvey Dent",
            "Maggie Gyllenhaal" to "Rachel Dawes",
            "Gary Oldman" to "Commissioner Gordon"
        )
    }
    
    val crew = remember {
        listOf(
            "Christopher Nolan" to "Director",
            "David S. Goyer" to "Writer",
            "Emma Thomas" to "Producer",
            "Wally Pfister" to "Cinematographer",
            "Hans Zimmer" to "Music"
        )
    }
    
    val runtime = "152 minutes"
    val budget = "$185 million"
    val boxOffice = "$1.005 billion"
    
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
                runtime = runtime,
                isWatched = isWatched,
                isInWatchlist = isInWatchlist,
                onWatchlistToggle = { 
                    isInWatchlist = !isInWatchlist
                    onWatchlistToggle(isInWatchlist)
                },
                onWatchedToggle = { 
                    isWatched = !isWatched
                    onWatchedToggle(isWatched)
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // User Rating Section
            UserRatingSection(
                userRating = userRating,
                userReview = userReview,
                onRatingChange = { rating ->
                    userRating = rating
                    onRatingChange(rating)
                },
                onReviewChange = { userReview = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Synopsis Section
            SynopsisSection(
                synopsis = movie.description
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Cast Section
            CastSection(cast = cast)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Crew Section
            CrewSection(crew = crew)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Movie Details Section
            MovieDetailsSection(
                releaseDate = movie.releaseYear,
                runtime = runtime,
                budget = budget,
                boxOffice = boxOffice,
                genres = movie.genres
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
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
