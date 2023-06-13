package com.example.ktorclient.response.allCharacterResponse

import kotlinx.serialization.Serializable

@Serializable
data class Origin(
    val name: String,
    val url: String
)