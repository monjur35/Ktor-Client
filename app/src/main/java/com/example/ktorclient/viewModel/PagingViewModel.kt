package com.example.ktorclient.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ktorclient.netWork.ApiService
import com.example.ktorclient.repo.CharacterRemoteMediator
import com.example.ktorclient.repo.TempSource
import com.example.ktorclient.response.allCharacterResponse.Result
import com.example.ktorclient.room.Database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject




@HiltViewModel
class PagingViewModel @Inject constructor(
    private val database: Database,
    private val apiService: ApiService
) : ViewModel() {
    @OptIn(ExperimentalPagingApi::class)
    fun getAllCharacters() : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                TempSource(apiService)
            }
           /* remoteMediator = CharacterRemoteMediator(apiService, database)*/
        ).flow

    }

    companion object {
        var PAGE_SIZE = 20
    }
}