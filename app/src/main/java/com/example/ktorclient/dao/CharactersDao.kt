package com.example.ktorclient.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktorclient.response.allCharacterResponse.Result

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters:List<Result>)

    @Query("Select * From tblCharacters ")
    fun getCharacters(): PagingSource<Int, Result>

    @Query("Delete From tblCharacters")
    suspend fun deleteAll()
}