package com.example.videri.data.di

import com.example.videri.data.database.DatabaseDriverFactory
import com.example.videri.data.database.DatabaseRepository
import com.example.videri.data.database.createDatabase
import com.example.videri.data.network.api.TMDBApiService
import com.example.videri.data.repositories.*
import com.example.videri.data.repositories.impl.*

open class DependencyContainer(databaseDriverFactory: DatabaseDriverFactory) {
    
    // Database
    private val database by lazy { createDatabase(databaseDriverFactory) }
    private val databaseRepository by lazy { DatabaseRepository(database) }
    
    // Network
    private val tmdbApiService by lazy { TMDBApiService() }
    
    // Repositories
    open val contentRepository: ContentRepository by lazy {
        // Use NetworkContentRepository for real API integration
        // Switch to MockContentRepository for testing
        NetworkContentRepository(tmdbApiService, databaseRepository)
    }
    
    open val userDataRepository: UserDataRepository by lazy {
        // Use DatabaseUserDataRepository for real persistence
        // Switch to MockUserDataRepository for testing
        DatabaseUserDataRepository(databaseRepository)
    }
    
    open val authRepository: AuthRepository by lazy {
        MockAuthRepository() // For now use mock - later implement real auth
    }
    
    val customListRepository: CustomListRepository by lazy {
        MockCustomListRepository() // Would implement database version
    }
    
    val recommendationRepository: RecommendationRepository by lazy {
        MockRecommendationRepository() // Would implement real recommendation engine
    }
    
    companion object {
        // For testing - provides mock implementations
        fun createMockContainer(): DependencyContainer {
            // This creates a container with mock implementations using in-memory database
            return object : DependencyContainer(MockDatabaseDriverFactory) {
                override val contentRepository: ContentRepository = MockContentRepository()
                override val userDataRepository: UserDataRepository = MockUserDataRepository()
                override val authRepository: AuthRepository = MockAuthRepository()
            }
        }
    }
}

// Mock database driver for testing
object MockDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): app.cash.sqldelight.db.SqlDriver {
        // For now, just throw - we'll use mock repositories anyway
        throw NotImplementedError("Mock database driver not implemented - using mock repositories instead")
    }
}
