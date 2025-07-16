package com.example.videri.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.videri.ui.screens.auth.LoginScreen
import com.example.videri.ui.screens.auth.SignUpScreen
import com.example.videri.ui.screens.home.HomeScreen
import com.example.videri.ui.screens.library.LibraryScreen
import com.example.videri.ui.icons.LineIcons
import com.example.videri.ui.components.ProfileSideSheet
import com.example.videri.ui.components.AppHeader
import com.example.videri.ui.components.SearchOverlay

enum class AuthScreen {
    Login,
    SignUp
}

enum class MainScreen {
    Home,
    Library,
    Calendar
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
                onForgotPasswordClick = { /* TODO: Implement forgot password */ },
                onGoogleSignInClick = {
                    // TODO: Implement Google Sign-In logic
                    onAuthSuccess()
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavigation(
    currentScreen: MainScreen,
    onScreenChange: (MainScreen) -> Unit,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ProfileSideSheet(
                onLogout = onLogout,
                onCloseDrawer = { 
                    scope.launch { drawerState.close() } 
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Content - Full screen
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
                                },
                                onOpenProfile = { 
                                    scope.launch { drawerState.open() } 
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
                                },
                                onOpenProfile = { 
                                    scope.launch { drawerState.open() } 
                                }
                            )
                        }

                        MainScreen.Calendar -> {
                            CalendarScreen(
                                onOpenProfile = { 
                                    scope.launch { drawerState.open() } 
                                },
                                onMovieClick = { /* TODO: Navigate to movie details */ },
                                onTVShowClick = { /* TODO: Navigate to TV show details */ }
                            )
                        }
                    }

                    // Floating Dock - Overlay at bottom
                    FloatingDock(
                        currentScreen = currentScreen,
                        onScreenChange = onScreenChange,
                        modifier = Modifier.align(androidx.compose.ui.Alignment.BottomCenter)
                    )
                }
            }
        }
    )
}

@Composable
private fun FloatingNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Icon with circular background and ripple effect
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                } else {
                    Color.Transparent
                }
            )
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    radius = 24.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            ),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.size(24.dp)
        )
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

@Composable
private fun CalendarScreen(
    onOpenProfile: () -> Unit,
    onMovieClick: (String) -> Unit,
    onTVShowClick: (String) -> Unit
) {
    var showSearchOverlay by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        AppHeader(
            title = "Calendar",
            onSearchClick = { showSearchOverlay = true },
            onProfileClick = onOpenProfile
        )
        
        // Calendar content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = LineIcons.Calendar,
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Calendar",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Track your viewing schedule, release dates, and upcoming shows",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
    
    // Search Overlay
    SearchOverlay(
        isVisible = showSearchOverlay,
        onDismiss = { showSearchOverlay = false },
        onMovieClick = onMovieClick,
        onTVShowClick = onTVShowClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FloatingDock(
    currentScreen: MainScreen,
    onScreenChange: (MainScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 32.dp, vertical = 24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        shape = RoundedCornerShape(36.dp),
        shadowElevation = 16.dp,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            FloatingNavItem(
                icon = LineIcons.Home,
                label = "Home",
                isSelected = currentScreen == MainScreen.Home,
                onClick = { onScreenChange(MainScreen.Home) }
            )
            FloatingNavItem(
                icon = LineIcons.Library,
                label = "Library",
                isSelected = currentScreen == MainScreen.Library,
                onClick = { onScreenChange(MainScreen.Library) }
            )
            FloatingNavItem(
                icon = LineIcons.Calendar,
                label = "Calendar",
                isSelected = currentScreen == MainScreen.Calendar,
                onClick = { onScreenChange(MainScreen.Calendar) }
            )
        }
    }
}
