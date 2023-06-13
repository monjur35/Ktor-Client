package com.example.ktorclient.netWork

import com.example.ktorclient.response.allCharacterResponse.AllCharacterResponse
import com.example.ktorclient.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import java.lang.Exception
import javax.inject.Inject


class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {


    override suspend fun getAllCharacter(): Resource<AllCharacterResponse> {
        return  try {
           Resource.Success( httpClient.get(ALL_CHARACTERS).body())
        }catch (e:Exception){
            Resource.Error(e)
        }
    }

    companion object {
        private val BASE_URL="https://rickandmortyapi.com/api/"
        private val ALL_CHARACTERS = "${BASE_URL}character"
    }
}