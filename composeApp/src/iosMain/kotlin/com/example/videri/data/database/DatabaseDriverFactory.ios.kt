package com.example.videri.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.videri.database.VideriDatabase

class IOSDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(VideriDatabase.Schema, "videri.db")
    }
}

actual fun createPlatformDatabaseDriverFactory(): DatabaseDriverFactory {
    return IOSDatabaseDriverFactory()
}
