package com.example.videri

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.videri.data.di.DependencyContainer
import com.example.videri.ui.navigation.AppNavigation
import com.example.videri.ui.theme.VideriTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(dependencyContainer: DependencyContainer? = null) {
    VideriTheme {
        val backgroundColor = MaterialTheme.colorScheme.background
        val surfaceColor = MaterialTheme.colorScheme.surface
        
        // Set system bars colors to match the actual theme colors
        SetSystemBarsColor(
            statusBarColor = backgroundColor,
            navigationBarColor = backgroundColor
        )
        
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            color = backgroundColor
        ) {
            // Use provided container or fallback to mock for preview/testing
            val container = dependencyContainer ?: DependencyContainer.createMockContainer()
            AppNavigation(container)
        }
    }
}

@Composable
expect fun SetSystemBarsColor(
    statusBarColor: androidx.compose.ui.graphics.Color,
    navigationBarColor: androidx.compose.ui.graphics.Color
)