package com.example.videri.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.videri.ui.theme.extendedColors
import com.example.videri.ui.icons.LineIcons

/**
 * Enhanced MovieCard using LineIcons
 */
@Composable
fun LineIconsMovieCard(
    title: String,
    posterUrl: String?,
    rating: Float,
    releaseYear: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isWatched: Boolean = false,
    isInWatchlist: Boolean = false
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Column {
            // Poster container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                // Placeholder for poster image
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = LineIcons.Movie,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
                
                // Status indicators with LineIcons
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isWatched) {
                        LineIconStatusChip(
                            icon = LineIcons.Check,
                            backgroundColor = MaterialTheme.extendedColors.watched,
                            contentColor = Color.White
                        )
                    }
                    if (isInWatchlist) {
                        LineIconStatusChip(
                            icon = LineIcons.Bookmark,
                            backgroundColor = MaterialTheme.extendedColors.watchlist,
                            contentColor = Color.White
                        )
                    }
                }
                
                // Rating overlay with LineIcons
                if (rating > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .background(
                                MaterialTheme.extendedColors.ratingBackground,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = LineIcons.Star,
                                contentDescription = null,
                                tint = MaterialTheme.extendedColors.rating,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = rating.toString().take(3),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.extendedColors.rating,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            
            // Content
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = releaseYear,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Enhanced StatusChip using LineIcons
 */
@Composable
fun LineIconStatusChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(12.dp)
        )
    }
}

/**
 * Enhanced navigation preview with LineIcons
 */
@Composable
fun LineIconsNavigationPreview() {
    var selectedTab by remember { mutableStateOf(0) }
    
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = LineIcons.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home") },
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 }
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = LineIcons.Search,
                    contentDescription = "Search"
                )
            },
            label = { Text("Search") },
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 }
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = LineIcons.Library,
                    contentDescription = "Library"
                )
            },
            label = { Text("Library") },
            selected = selectedTab == 2,
            onClick = { selectedTab = 2 }
        )
        
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = LineIcons.User,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") },
            selected = selectedTab == 3,
            onClick = { selectedTab = 3 }
        )
    }
}
