package com.example.videri

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SetSystemBarsColor(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val isSystemInDarkTheme = isSystemInDarkTheme()
    
    LaunchedEffect(statusBarColor, navigationBarColor, isSystemInDarkTheme) {
        // iOS automatically adapts to system theme, but we can still control the status bar style
        val statusBarStyle = if (isSystemInDarkTheme) {
            // Dark theme: Use light content (white text/icons)
            UIStatusBarStyleLightContent
        } else {
            // Light theme: Use dark content (black text/icons)
            UIStatusBarStyleDarkContent
        }
        
        // Set the status bar style based on system theme
        UIApplication.sharedApplication.setStatusBarStyle(statusBarStyle, animated = true)
        
        // Note: iOS handles system bars more automatically than Android
        // The system respects the app's color scheme and user's theme preference
        // The home indicator and other system elements adapt automatically
    }
}
