package com.example.ktorclient.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ktorclient.dao.CharactersDao
import com.example.ktorclient.dao.RemoteKeysDao
import com.example.ktorclient.response.allCharacterResponse.RemoteKeys
import com.example.ktorclient.response.allCharacterResponse.Result

@Database(entities = [RemoteKeys::class,Result::class], version = 2)
abstract class Database: RoomDatabase() {
    abstract fun getRemoteKeyDao():RemoteKeysDao
    abstract fun getCharacterDao():CharactersDao

}