package com.example.videri.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Custom theme extension for content-specific colors
data class ExtendedColors(
    val rating: Color,
    val ratingBackground: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val watched: Color,
    val watchlist: Color,
    val inProgress: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        rating = Color.Unspecified,
        ratingBackground = Color.Unspecified,
        success = Color.Unspecified,
        warning = Color.Unspecified,
        info = Color.Unspecified,
        watched = Color.Unspecified,
        watchlist = Color.Unspecified,
        inProgress = Color.Unspecified
    )
}

// Extension property to access extended colors
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current

@Composable
fun VideriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val extendedColors = ExtendedColors(
        rating = ContentColors.Rating,
        ratingBackground = ContentColors.RatingBackground,
        success = ContentColors.Success,
        warning = ContentColors.Warning,
        info = ContentColors.Info,
        watched = ContentColors.Watched,
        watchlist = ContentColors.Watchlist,
        inProgress = ContentColors.InProgress
    )

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
