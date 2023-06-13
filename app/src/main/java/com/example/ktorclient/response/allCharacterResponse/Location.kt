package com.example.ktorclient.response.allCharacterResponse

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val url: String
)