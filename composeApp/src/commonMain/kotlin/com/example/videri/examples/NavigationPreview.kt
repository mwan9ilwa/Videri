package com.example.videri.examples

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

/**
 * Preview component showing the updated navigation with Material Design icons
 */
@Composable
fun NavigationPreview() {
    var selectedTab by remember { mutableStateOf(0) }
    
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
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
                    imageVector = Icons.Default.Search,
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
                    imageVector = Icons.Default.VideoLibrary,
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
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile") },
            selected = selectedTab == 3,
            onClick = { selectedTab = 3 }
        )
    }
}
