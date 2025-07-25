package com.example.videri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val application = application as VideriApplication
            App(dependencyContainer = application.dependencyContainer)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}