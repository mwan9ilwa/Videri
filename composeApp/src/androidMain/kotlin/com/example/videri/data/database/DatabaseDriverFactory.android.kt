package com.example.videri.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.videri.database.VideriDatabase

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(VideriDatabase.Schema, context, "videri.db")
    }
}

actual fun createPlatformDatabaseDriverFactory(): DatabaseDriverFactory {
    // This would need context - for now return a placeholder that throws
    return object : DatabaseDriverFactory {
        override fun createDriver(): SqlDriver {
            throw NotImplementedError("Android driver factory needs context - use AndroidDatabaseDriverFactory directly")
        }
    }
}
