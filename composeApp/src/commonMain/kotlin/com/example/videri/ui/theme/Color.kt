package com.example.videri.ui.theme

import androidx.compose.ui.graphics.Color

// Light Theme Colors
object LightColors {
    val Primary = Color(0xFF6C5CE7)
    val PrimaryVariant = Color(0xFF5A4FCF)
    val Secondary = Color(0xFF74B9FF)
    val SecondaryVariant = Color(0xFF0984E3)
    val Background = Color(0xFFF8F9FA)
    val Surface = Color(0xFFFf5f2fd)
    val SurfaceVariant = Color(0xFFF1F3F4)
    val Error = Color(0xFFE74C3C)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF2D3436)
    val OnSurface = Color(0xFF2D3436)
    val OnSurfaceVariant = Color(0xFF636E72)
    val OnError = Color(0xFFFFFFFF)
    val Outline = Color(0xFFDDD8DD)
    val OutlineVariant = Color(0xFFE1E5E9)
    val Scrim = Color(0x80000000)
}

// Dark Theme Colors
object DarkColors {
    val Primary = Color(0xFF8B7FF5)
    val PrimaryVariant = Color(0xFF6C5CE7)
    val Secondary = Color(0xFF74B9FF)
    val SecondaryVariant = Color(0xFF0984E3)
    val Background = Color(0xFF0D1117)
    val Surface = Color(0xFF161B22)
    val SurfaceVariant = Color(0xFF21262D)
    val Error = Color(0xFFE74C3C)
    val OnPrimary = Color(0xFF000000)
    val OnSecondary = Color(0xFF000000)
    val OnBackground = Color(0xFFE6EDF3)
    val OnSurface = Color(0xFFE6EDF3)
    val OnSurfaceVariant = Color(0xFF8B949E)
    val OnError = Color(0xFFFFFFFF)
    val Outline = Color(0xFF30363D)
    val OutlineVariant = Color(0xFF21262D)
    val Scrim = Color(0x80000000)
}

// Content-specific colors
object ContentColors {
    val Rating = Color(0xFFF1C40F)
    val RatingBackground = Color(0x33F1C40F)
    val Success = Color(0xFF00B894)
    val Warning = Color(0xFFE17055)
    val Info = Color(0xFF0984E3)
    val Watched = Color(0xFF00B894)
    val Watchlist = Color(0xFF6C5CE7)
    val InProgress = Color(0xFFE17055)
}

// Gradient colors
object GradientColors {
    val PrimaryGradient = listOf(
        Color(0xFF6C5CE7),
        Color(0xFF74B9FF)
    )
    val SecondaryGradient = listOf(
        Color(0xFF00B894),
        Color(0xFF74B9FF)
    )
    val BackgroundGradient = listOf(
        Color(0xFF0D1117),
        Color(0xFF161B22)
    )
}
