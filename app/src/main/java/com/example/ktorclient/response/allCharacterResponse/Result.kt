package com.example.ktorclient.response.allCharacterResponse

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ktorclient.utils.StringListConverter
import kotlinx.serialization.Serializable

@Entity(tableName = "tblCharacters")
@Serializable
@TypeConverters(StringListConverter::class)
data class Result(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val created: String,
    val episode: List<String>?=null,
    val gender: String,
    val image: String,
   /* @Embedded
    val location: Location,*/
    @ColumnInfo(name = "character_name")
    val name: String,
  /*  @Embedded
    val origin: Origin,*/
    val species: String,
    val status: String,
    val type: String,
    val url: String,
 /*   @ColumnInfo(name = "page")
    var page: Int?=0,*/
)