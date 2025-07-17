package com.example.videri

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun SetSystemBarsColor(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            // Set status bar icons to dark if the status bar color is light.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                statusBarColor.luminance() > 0.5

            // Set navigation bar icons to dark if the navigation bar color is light.
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                navigationBarColor.luminance() > 0.5
        }
    }
}