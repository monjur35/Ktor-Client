package com.example.ktorclient.response.allCharacterResponse

import kotlinx.serialization.Serializable

@Serializable
data class AllCharacterResponse(
    val info: Info,
    val results: List<Result>
)