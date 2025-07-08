package com.example.videri

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform