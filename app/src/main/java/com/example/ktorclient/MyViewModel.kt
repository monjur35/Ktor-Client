package com.example.ktorclient

import android.graphics.Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorclient.response.allCharacterResponse.AllCharacterResponse
import com.example.ktorclient.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    fun getAllCharacters(): MutableLiveData<Resource<AllCharacterResponse>?> {
         val allCharacterResponse = MutableLiveData<Resource<AllCharacterResponse>?>(null)
        viewModelScope.launch {
            val response=repository.getAllCharacters()
            withContext(Dispatchers.Main){
                allCharacterResponse.value=response
            }
        }
        return allCharacterResponse
    }
}