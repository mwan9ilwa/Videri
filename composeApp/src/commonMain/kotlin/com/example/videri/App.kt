package com.example.videri

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.videri.ui.navigation.AppNavigation
import com.example.videri.ui.theme.VideriTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    VideriTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            color = androidx.compose.material3.MaterialTheme.colorScheme.background
        ) {
            AppNavigation()
        }
    }
}