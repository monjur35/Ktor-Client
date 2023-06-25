package com.example.ktorclient.response.allCharacterResponse

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class Origin(
    @ColumnInfo(name = "origin_name")
    val name: String,
    @ColumnInfo(name = "origin_url")
    val url: String
)