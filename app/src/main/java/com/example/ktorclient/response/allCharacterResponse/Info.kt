package com.example.ktorclient.response.allCharacterResponse

import kotlinx.serialization.Serializable

@Serializable
data class Info(
    val count: Int,
    val next: String,
    var pages: Int,
    val prev: String?
)