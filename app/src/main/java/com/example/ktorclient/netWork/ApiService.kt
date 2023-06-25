package com.example.ktorclient.netWork

import com.example.ktorclient.response.allCharacterResponse.AllCharacterResponse
import com.example.ktorclient.utils.Resource

interface ApiService {
    suspend fun getAllCharacter(): Resource<AllCharacterResponse>

    suspend fun getPaginatedCharacter(page:Int):AllCharacterResponse
}