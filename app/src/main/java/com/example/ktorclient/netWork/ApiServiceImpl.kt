package com.example.ktorclient.netWork

import android.util.Log
import com.example.ktorclient.response.allCharacterResponse.AllCharacterResponse
import com.example.ktorclient.utils.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import kotlin.Exception


class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {


    override suspend fun getAllCharacter(): Resource<AllCharacterResponse> {


        return  try {

           Resource.Success( httpClient.get(ALL_CHARACTERS).body())
        }catch (e:Exception){
            Resource.Error(e)
        }
    }

    override suspend fun getPaginatedCharacter(page:Int): AllCharacterResponse {
        Log.e("TAG", "getPaginatedCharacter: "+httpClient.get("$ALL_CHARACTERS?page=$page").body())
        return httpClient.get("$ALL_CHARACTERS?page=$page").body()
    }

    companion object {
        private val BASE_URL="https://rickandmortyapi.com/api/"
        private val ALL_CHARACTERS = "${BASE_URL}character"
    }
}