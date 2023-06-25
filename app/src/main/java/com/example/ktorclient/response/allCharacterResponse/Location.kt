package com.example.ktorclient.response.allCharacterResponse

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @ColumnInfo(name = "location_name")
    val name: String,
    @ColumnInfo(name = "location_url")
    val url: String
)