package com.example.ktorclient.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktorclient.response.allCharacterResponse.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From tbl_remote_key Where characterId = :id")
    suspend fun getRemoteKeyByMovieID(id: Int): RemoteKeys?

    @Query("Delete From tblCharacters")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From tbl_remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}