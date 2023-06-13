package com.example.ktorclient

import com.example.ktorclient.netWork.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllCharacters()=apiService.getAllCharacter()
}