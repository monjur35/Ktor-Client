package com.example.ktorclient.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ktorclient.netWork.ApiService
import com.example.ktorclient.response.allCharacterResponse.Result
import com.example.ktorclient.viewModel.PagingViewModel
import java.io.IOException

class TempSource(
    private val apiService: ApiService,
) :
    PagingSource<Int, Result>() {




    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {

            val nextPage=params.key?: 1



            val response=apiService.getPaginatedCharacter(nextPage)
            var nextPageNumber:Int?= null
            val prevKey=response.info.prev



            if (response != null){
                val nextUrl=extractPageNumber(response.info.next)
                nextPageNumber= nextUrl?.plus(1)
                PagingViewModel.PAGE_SIZE= response.info.pages
            }
            LoadResult.Page(data = response.results, prevKey = null, nextKey = if (nextPage>= response.info.pages) null else nextPageNumber)

        }
        catch (e:Exception){
            Log.e("TAG", "load: " +e.localizedMessage )
            errorMessage.postValue(e.localizedMessage)
            e.printStackTrace()
            LoadResult.Error(e)

        }
        catch (e: IOException){
            errorMessage.postValue(e.localizedMessage)
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean
        get() = super.keyReuseSupported


    companion object{
        var errorMessage = MutableLiveData<String>()
    }

    fun extractPageNumber(url: String): Int? {
        val regex = """page=(\d+)""".toRegex()
        val matchResult = regex.find(url)
        Log.e("TAG", "extractPageNumber:${matchResult?.groupValues?.get(0)?.replace("page=","")?.toInt()} ", )
        return matchResult?.groupValues?.getOrNull(1)?.toInt()
    }
}