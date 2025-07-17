package com.example.videri.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import com.example.videri.ui.screens.home.TVShow
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons

data class Episode(
    val id: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val title: String,
    val description: String,
    val duration: String = "45 min",
    val isWatched: Boolean = false
)

data class Season(
    val number: Int,
    val episodeCount: Int,
    val episodes: List<Episode>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVShowDetailScreen(
    tvShow: TVShow,
    onBackClick: () -> Unit,
    onWatchlistToggle: (Boolean) -> Unit,
    onWatchedToggle: (Boolean) -> Unit,
    onEpisodeWatchedToggle: (String, Boolean) -> Unit,
    onRatingChange: (Float) -> Unit,
    onAddToList: () -> Unit,
    modifier: Modifier = Modifier
) {
    var userRating by remember { mutableStateOf(0f) }
    var userReview by remember { mutableStateOf("") }
    var isWatched by remember { mutableStateOf(tvShow.isWatched) }
    var isInWatchlist by remember { mutableStateOf(tvShow.isInWatchlist) }
    
    // Mock seasons and episodes data
    val seasons = remember {
        listOf(
            Season(
                number = 1,
                episodeCount = 9,
                episodes = listOf(
                    Episode(
                        id = "s1e1",
                        seasonNumber = 1,
                        episodeNumber = 1,
                        title = "When You're Lost in the Darkness",
                        description = "Twenty years after a fungal outbreak ravages the planet, survivors Joel and Tess are tasked with a mission that could change everything.",
                        isWatched = true
                    ),
                    Episode(
                        id = "s1e2",
                        seasonNumber = 1,
                        episodeNumber = 2,
                        title = "Infected",
                        description = "After escaping the QZ, Joel and Tess clash over Ellie's fate while navigating the ruins of a long-abandoned Boston.",
                        isWatched = true
                    ),
                    Episode(
                        id = "s1e3",
                        seasonNumber = 1,
                        episodeNumber = 3,
                        title = "Long, Long Time",
                        description = "Joel and Ellie's journey continues as they encounter Bill and Frank, revealing the story of their survival.",
                        isWatched = false
                    )
                )
            ),
            Season(
                number = 2,
                episodeCount = 7,
                episodes = listOf(
                    Episode(
                        id = "s2e1",
                        seasonNumber = 2,
                        episodeNumber = 1,
                        title = "Back to the Wall",
                        description = "Five years after the events of Season 1, Joel and Ellie encounter new threats.",
                        isWatched = false
                    )
                )
            )
        )
    }
    
    val cast = remember {
        listOf(
            "Pedro Pascal" to "Joel Miller",
            "Bella Ramsey" to "Ellie Williams",
            "Anna Torv" to "Tess Servopoulos",
            "Lamar Johnson" to "Henry Burrell",
            "Keivonn Woodard" to "Sam Burrell"
        )
    }
    
    val crew = remember {
        listOf(
            "Craig Mazin" to "Creator",
            "Neil Druckmann" to "Creator",
            "Carolyn Strauss" to "Executive Producer",
            "Evan Wells" to "Executive Producer",
            "Asad Qizilbash" to "Executive Producer"
        )
    }
    
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
            TVShowHeroSection(
                tvShow = tvShow,
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
            
            // Progress Section
            ProgressSection(
                seasons = seasons,
                currentProgress = tvShow.episodeProgress
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
                synopsis = tvShow.description
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Episodes Section
            EpisodesSection(
                seasons = seasons,
                onEpisodeWatchedToggle = onEpisodeWatchedToggle
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Cast Section
            CastSection(cast = cast)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Crew Section
            CrewSection(crew = crew)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Show Details Section
            TVShowDetailsSection(
                releaseDate = tvShow.releaseYear,
                status = tvShow.status,
                genres = tvShow.genres,
                seasons = seasons
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
private fun TVShowHeroSection(
    tvShow: TVShow,
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
                    imageVector = LineIcons.Television,
                    contentDescription = "TV show poster",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
            
            // TV Show Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tvShow.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${tvShow.releaseYear} • ${tvShow.status}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (tvShow.episodeProgress != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Currently: ${tvShow.episodeProgress}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
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
                        text = tvShow.rating.toString(),
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
                            text = if (isWatched) "Completed" else "Mark as Completed",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProgressSection(
    seasons: List<Season>,
    currentProgress: String?
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Progress",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        val totalEpisodes = seasons.sumOf { it.episodeCount }
        val watchedEpisodes = seasons.sumOf { season ->
            season.episodes.count { it.isWatched }
        }
        
        // Progress Bar
        val progress = if (totalEpisodes > 0) watchedEpisodes.toFloat() / totalEpisodes else 0f
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$watchedEpisodes / $totalEpisodes episodes",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        
        if (currentProgress != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Currently watching: $currentProgress",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun EpisodesSection(
    seasons: List<Season>,
    onEpisodeWatchedToggle: (String, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Episodes",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        seasons.forEach { season ->
            SeasonSection(
                season = season,
                onEpisodeWatchedToggle = onEpisodeWatchedToggle
            )
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SeasonSection(
    season: Season,
    onEpisodeWatchedToggle: (String, Boolean) -> Unit
) {
    var isExpanded by remember { mutableStateOf(season.number == 1) }
    
    Column {
        // Season Header
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            onClick = { isExpanded = !isExpanded },
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Season ${season.number}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    val watchedCount = season.episodes.count { it.isWatched }
                    Text(
                        text = "$watchedCount/${season.episodeCount} episodes watched",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Episodes List
        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))
            
            season.episodes.forEach { episode ->
                EpisodeItem(
                    episode = episode,
                    onWatchedToggle = { onEpisodeWatchedToggle(episode.id, !episode.isWatched) }
                )
                
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun EpisodeItem(
    episode: Episode,
    onWatchedToggle: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "E${episode.episodeNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = episode.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = episode.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = episode.duration,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Watch status toggle
            Surface(
                onClick = onWatchedToggle,
                shape = RoundedCornerShape(8.dp),
                color = if (episode.isWatched) MaterialTheme.extendedColors.watched else MaterialTheme.colorScheme.surfaceVariant
            ) {
                Icon(
                    imageVector = LineIcons.Check,
                    contentDescription = if (episode.isWatched) "Mark as unwatched" else "Mark as watched",
                    tint = if (episode.isWatched) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Composable
private fun TVShowDetailsSection(
    releaseDate: String,
    status: String,
    genres: List<String>,
    seasons: List<Season>
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
            "First Aired" to releaseDate,
            "Status" to status,
            "Seasons" to seasons.size.toString(),
            "Total Episodes" to seasons.sumOf { it.episodeCount }.toString()
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

// Reusing components from MovieDetailScreen
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
            placeholder = "Share your thoughts about this show...",
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
