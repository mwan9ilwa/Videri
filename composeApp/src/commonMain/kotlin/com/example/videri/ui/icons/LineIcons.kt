@file:OptIn(ExperimentalResourceApi::class)

package com.example.videri.ui.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import videri.composeapp.generated.resources.*

/**
 * LineIcons object providing access to LineIcons SVG vectors
 * for consistent iconography throughout the Videri app.
 */
object LineIcons {
    
    // Navigation Icons
    val Home: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_home)
    
    val Search: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_search)
    
    val Library: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_library)
    
    val User: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_user)
    
    val Calendar: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_calendar)
    
    // Media Icons
    val Movie: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_movie)
    
    val Television: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_television)
    
    // Rating & Status Icons
    val Star: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_star)
    
    val Check: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_check)
    
    val Bookmark: ImageVector
        @Composable get() = vectorResource(Res.drawable.lineicons_bookmark)
}
