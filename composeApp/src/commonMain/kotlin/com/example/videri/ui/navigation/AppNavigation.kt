package com.example.videri.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.videri.ui.screens.auth.LoginScreen
import com.example.videri.ui.screens.auth.SignUpScreen
import com.example.videri.ui.screens.home.HomeScreen
import com.example.videri.ui.screens.library.LibraryScreen
import com.example.videri.ui.screens.profile.ProfileScreen
import com.example.videri.ui.screens.search.SearchScreen

enum class AuthScreen {
    Login,
    SignUp
}

enum class MainScreen {
    Home,
    Search,
    Library,
    Profile
}

@Composable
fun AppNavigation() {
    var isAuthenticated by remember { mutableStateOf(false) }
    var currentAuthScreen by remember { mutableStateOf(AuthScreen.Login) }
    var currentMainScreen by remember { mutableStateOf(MainScreen.Home) }

    if (isAuthenticated) {
        MainNavigation(
            currentScreen = currentMainScreen,
            onScreenChange = { currentMainScreen = it },
            onLogout = { isAuthenticated = false }
        )
    } else {
        AuthNavigation(
            currentScreen = currentAuthScreen,
            onScreenChange = { currentAuthScreen = it },
            onAuthSuccess = { isAuthenticated = true }
        )
    }
}

@Composable
private fun AuthNavigation(
    currentScreen: AuthScreen,
    onScreenChange: (AuthScreen) -> Unit,
    onAuthSuccess: () -> Unit
) {
    when (currentScreen) {
        AuthScreen.Login -> {
            LoginScreen(
                onLoginClick = { email, password ->
                    // TODO: Implement login logic
                    onAuthSuccess()
                },
                onSignUpClick = { onScreenChange(AuthScreen.SignUp) },
                onForgotPasswordClick = { /* TODO: Implement forgot password */ }
            )
        }

        AuthScreen.SignUp -> {
            SignUpScreen(
                onSignUpClick = { name, email, password ->
                    // TODO: Implement sign up logic
                    onAuthSuccess()
                },
                onLoginClick = { onScreenChange(AuthScreen.Login) }
            )
        }
    }
}

@Composable
private fun MainNavigation(
    currentScreen: MainScreen,
    onScreenChange: (MainScreen) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Content
        Box(
            modifier = Modifier.weight(1f)
        ) {
            when (currentScreen) {
                MainScreen.Home -> {
                    HomeScreen(
                        onMovieClick = { movieId ->
                            // TODO: Navigate to movie details
                        },
                        onTVShowClick = { tvShowId ->
                            // TODO: Navigate to TV show details
                        },
                        onSeeAllClick = { category ->
                            // TODO: Navigate to category screen
                        }
                    )
                }

                MainScreen.Search -> {
                    SearchScreen(
                        onMovieClick = { movieId ->
                            // TODO: Navigate to movie details
                        },
                        onTVShowClick = { tvShowId ->
                            // TODO: Navigate to TV show details
                        }
                    )
                }

                MainScreen.Library -> {
                    LibraryScreen(
                        onMovieClick = { movieId ->
                            // TODO: Navigate to movie details
                        },
                        onTVShowClick = { tvShowId ->
                            // TODO: Navigate to TV show details
                        }
                    )
                }

                MainScreen.Profile -> {
                    ProfileScreen(
                        onLogout = onLogout
                    )
                }
            }
        }

        // Bottom Navigation
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            NavigationBarItem(
                icon = {
                    Text(
                        text = "🏠",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                label = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentScreen == MainScreen.Home,
                onClick = { onScreenChange(MainScreen.Home) }
            )
            NavigationBarItem(
                icon = {
                    Text(
                        text = "🔍",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                label = {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentScreen == MainScreen.Search,
                onClick = { onScreenChange(MainScreen.Search) }
            )
            NavigationBarItem(
                icon = {
                    Text(
                        text = "📚",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                label = {
                    Text(
                        text = "Library",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentScreen == MainScreen.Library,
                onClick = { onScreenChange(MainScreen.Library) }
            )
            NavigationBarItem(
                icon = {
                    Text(
                        text = "👤",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                label = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = currentScreen == MainScreen.Profile,
                onClick = { onScreenChange(MainScreen.Profile) }
            )
        }
    }
}

@Composable
private fun LibraryPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "📚",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Library Screen",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your watchlist, watched movies, and custom lists will appear here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
