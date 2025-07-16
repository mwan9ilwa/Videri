package com.example.videri

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
actual fun SetSystemBarsColor(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isSystemInDarkTheme = isSystemInDarkTheme()
    
    LaunchedEffect(statusBarColor, navigationBarColor, isSystemInDarkTheme) {
        activity?.let { act ->
            act.window?.let { window ->
                // Get system bars controller
                val systemBarsController = WindowCompat.getInsetsController(window, window.decorView)
                
                // For adaptive system bars, we'll use a more intelligent approach
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // On Android 11+, we can make the bars more adaptive
                    if (isSystemInDarkTheme) {
                        // Dark theme: Use darker system bars with light content
                        window.statusBarColor = Color.Black.copy(alpha = 0.3f).toArgb()
                        window.navigationBarColor = Color.Black.copy(alpha = 0.3f).toArgb()
                        systemBarsController.isAppearanceLightStatusBars = false
                        systemBarsController.isAppearanceLightNavigationBars = false
                    } else {
                        // Light theme: Use lighter system bars with dark content
                        window.statusBarColor = Color.White.copy(alpha = 0.9f).toArgb()
                        window.navigationBarColor = Color.White.copy(alpha = 0.9f).toArgb()
                        systemBarsController.isAppearanceLightStatusBars = true
                        systemBarsController.isAppearanceLightNavigationBars = true
                    }
                } else {
                    // On older versions, use the app's background colors but adapt content
                    window.statusBarColor = statusBarColor.toArgb()
                    window.navigationBarColor = navigationBarColor.toArgb()
                    
                    // Set content color based on system theme preference
                    if (isSystemInDarkTheme) {
                        systemBarsController.isAppearanceLightStatusBars = false
                        systemBarsController.isAppearanceLightNavigationBars = false
                    } else {
                        systemBarsController.isAppearanceLightStatusBars = true
                        systemBarsController.isAppearanceLightNavigationBars = true
                    }
                }
                
                // Set navigation bar divider color for Android 8.1+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                    val dividerColor = if (isSystemInDarkTheme) {
                        Color.White.copy(alpha = 0.1f)
                    } else {
                        Color.Black.copy(alpha = 0.1f)
                    }
                    window.navigationBarDividerColor = dividerColor.toArgb()
                }
            }
        }
    }
}
