package com.example.videri.data.database

import app.cash.sqldelight.db.SqlDriver
import com.example.videri.database.VideriDatabase

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

// Platform-specific implementation will be provided via expect/actual
expect fun createPlatformDatabaseDriverFactory(): DatabaseDriverFactory

fun createDatabase(driverFactory: DatabaseDriverFactory): VideriDatabase {
    val driver = driverFactory.createDriver()
    return VideriDatabase(driver)
}
