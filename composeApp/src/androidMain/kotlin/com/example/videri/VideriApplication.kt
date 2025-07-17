package com.example.videri

import android.app.Application
import com.example.videri.data.database.AndroidDatabaseDriverFactory
import com.example.videri.data.di.DependencyContainer

class VideriApplication : Application() {
    
    lateinit var dependencyContainer: DependencyContainer
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize dependency container with real database
        val databaseDriverFactory = AndroidDatabaseDriverFactory(this)
        dependencyContainer = DependencyContainer(databaseDriverFactory)
    }
}
