package com.example.videri.ui.screens.lists

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.videri.ui.components.*
import com.example.videri.ui.screens.home.Movie
import com.example.videri.ui.screens.home.TVShow
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons

data class CustomList(
    val id: String,
    val name: String,
    val description: String,
    val isPublic: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TVShow> = emptyList(),
    val createdAt: String = "",
    val updatedAt: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomListsScreen(
    onBackClick: () -> Unit,
    onListClick: (String) -> Unit,
    onCreateList: (String, String, Boolean) -> Unit,
    onDeleteList: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    
    // Mock data
    val customLists = remember {
        listOf(
            CustomList(
                id = "1",
                name = "Favorite Sci-Fi Movies",
                description = "My collection of the best science fiction films",
                isPublic = true,
                movies = listOf(
                    Movie(
                        id = "1",
                        title = "Blade Runner 2049",
                        posterUrl = null,
                        rating = 8.0f,
                        releaseYear = "2017",
                        description = "Young Blade Runner K's discovery of a long-buried secret leads him to track down former Blade Runner Rick Deckard.",
                        genres = listOf("Sci-Fi", "Drama", "Thriller")
                    ),
                    Movie(
                        id = "2",
                        title = "Interstellar",
                        posterUrl = null,
                        rating = 8.6f,
                        releaseYear = "2014",
                        description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                        genres = listOf("Adventure", "Drama", "Sci-Fi")
                    )
                ),
                createdAt = "2024-01-15",
                updatedAt = "2024-01-20"
            ),
            CustomList(
                id = "2",
                name = "Must-Watch Documentaries",
                description = "Educational and thought-provoking documentaries",
                isPublic = false,
                movies = listOf(
                    Movie(
                        id = "3",
                        title = "Free Solo",
                        posterUrl = null,
                        rating = 8.1f,
                        releaseYear = "2018",
                        description = "Follow Alex Honnold as he attempts to become the first person to ever free solo climb Yosemite's 3,000-foot high El Capitan wall.",
                        genres = listOf("Documentary", "Adventure", "Sport")
                    )
                ),
                createdAt = "2024-01-10",
                updatedAt = "2024-01-18"
            ),
            CustomList(
                id = "3",
                name = "Binge-Worthy Crime Series",
                description = "The best crime and thriller TV shows",
                isPublic = true,
                tvShows = listOf(
                    TVShow(
                        id = "1",
                        title = "True Detective",
                        posterUrl = null,
                        rating = 9.0f,
                        releaseYear = "2014",
                        description = "Seasonal anthology series in which police investigations unearth the personal and professional secrets of those involved.",
                        genres = listOf("Crime", "Drama", "Mystery"),
                        status = "Returning"
                    )
                ),
                createdAt = "2024-01-05",
                updatedAt = "2024-01-22"
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
                    text = "Custom Lists",
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
                IconButton(onClick = { showCreateDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create new list"
                    )
                }
            }
        )
        
        // Content
        if (customLists.isEmpty()) {
            EmptyListsState(
                onCreateList = { showCreateDialog = true }
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(customLists) { list ->
                    CustomListCard(
                        list = list,
                        onClick = { onListClick(list.id) },
                        onDelete = { onDeleteList(list.id) }
                    )
                }
            }
        }
    }
    
    // Create List Dialog
    if (showCreateDialog) {
        CreateListDialog(
            onDismiss = { showCreateDialog = false },
            onCreateList = { name, description, isPublic ->
                onCreateList(name, description, isPublic)
                showCreateDialog = false
            }
        )
    }
}

@Composable
private fun EmptyListsState(
    onCreateList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = LineIcons.Bookmark,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Custom Lists Yet",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Create personalized lists to organize your favorite movies and TV shows",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        VideriButton(
            onClick = onCreateList,
            variant = ButtonVariant.Primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create Your First List")
        }
    }
}

@Composable
private fun CustomListCard(
    list: CustomList,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = list.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = list.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (list.isPublic) {
                        StatusChip(
                            text = "Public",
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        StatusChip(
                            text = "Private",
                            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    IconButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete list",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Stats
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (list.movies.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = LineIcons.Movie,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${list.movies.size} movies",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                if (list.tvShows.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = LineIcons.Television,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${list.tvShows.size} TV shows",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Last updated
            Text(
                text = "Updated ${list.updatedAt}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(text = "Delete List")
            },
            text = {
                Text(text = "Are you sure you want to delete \"${list.name}\"? This action cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete()
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun CreateListDialog(
    onDismiss: () -> Unit,
    onCreateList: (String, String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(false) }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Create New List",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VideriTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "List Name",
                    placeholder = "e.g., Favorite Action Movies",
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                VideriTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description",
                    placeholder = "Brief description of your list",
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    singleLine = false
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Make list public",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Switch(
                        checked = isPublic,
                        onCheckedChange = { isPublic = it }
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VideriButton(
                        onClick = onDismiss,
                        variant = ButtonVariant.Secondary,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    
                    VideriButton(
                        onClick = {
                            if (name.isNotBlank()) {
                                onCreateList(name, description, isPublic)
                            }
                        },
                        variant = ButtonVariant.Primary,
                        modifier = Modifier.weight(1f),
                        enabled = name.isNotBlank()
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomListDetailScreen(
    listId: String,
    onBackClick: () -> Unit,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit,
    onAddContent: () -> Unit,
    onRemoveContent: (String, String) -> Unit, // contentId, contentType
    modifier: Modifier = Modifier
) {
    // Mock data - in real app, fetch by listId
    val customList = remember {
        CustomList(
            id = listId,
            name = "Favorite Sci-Fi Movies",
            description = "My collection of the best science fiction films",
            isPublic = true,
            movies = listOf(
                Movie(
                    id = "1",
                    title = "Blade Runner 2049",
                    posterUrl = null,
                    rating = 8.0f,
                    releaseYear = "2017",
                    description = "Young Blade Runner K's discovery of a long-buried secret leads him to track down former Blade Runner Rick Deckard.",
                    genres = listOf("Sci-Fi", "Drama", "Thriller")
                ),
                Movie(
                    id = "2",
                    title = "Interstellar",
                    posterUrl = null,
                    rating = 8.6f,
                    releaseYear = "2014",
                    description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                    genres = listOf("Adventure", "Drama", "Sci-Fi")
                )
            ),
            createdAt = "2024-01-15",
            updatedAt = "2024-01-20"
        )
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { 
                Text(
                    text = customList.name,
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
                IconButton(onClick = { /* TODO: Edit list */ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit list"
                    )
                }
                IconButton(onClick = onAddContent) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add content"
                    )
                }
            }
        )
        
        // Content
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // List info
            item {
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
                            text = customList.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "${customList.movies.size + customList.tvShows.size} items",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            
                            if (customList.isPublic) {
                                Text(
                                    text = "Public",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            
            // Movies
            if (customList.movies.isNotEmpty()) {
                item {
                    Text(
                        text = "Movies (${customList.movies.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                items(customList.movies) { movie ->
                    ContentCard(
                        title = movie.title,
                        posterUrl = movie.posterUrl,
                        rating = movie.rating,
                        releaseYear = movie.releaseYear,
                        description = movie.description,
                        genres = movie.genres,
                        onClick = { onMovieClick(movie.id) },
                        isWatched = movie.isWatched,
                        isInWatchlist = movie.isInWatchlist,
                        type = "movie"
                    )
                }
            }
            
            // TV Shows
            if (customList.tvShows.isNotEmpty()) {
                item {
                    Text(
                        text = "TV Shows (${customList.tvShows.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                items(customList.tvShows) { show ->
                    ContentCard(
                        title = show.title,
                        posterUrl = show.posterUrl,
                        rating = show.rating,
                        releaseYear = show.releaseYear,
                        description = show.description,
                        genres = show.genres,
                        onClick = { onTVShowClick(show.id) },
                        isWatched = show.isWatched,
                        isInWatchlist = show.isInWatchlist,
                        type = "tv"
                    )
                }
            }
            
            // Empty state
            if (customList.movies.isEmpty() && customList.tvShows.isEmpty()) {
                item {
                    EmptyState(
                        title = "List is empty",
                        description = "Add movies and TV shows to your list",
                        icon = "📝",
                        action = {
                            VideriButton(
                                onClick = onAddContent,
                                variant = ButtonVariant.Primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add Content")
                            }
                        }
                    )
                }
            }
        }
    }
}
