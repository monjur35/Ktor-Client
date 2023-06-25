package com.example.ktorclient.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.ktorclient.netWork.ApiService
import com.example.ktorclient.response.allCharacterResponse.RemoteKeys
import com.example.ktorclient.response.allCharacterResponse.Result
import com.example.ktorclient.room.Database
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val initialPage: Int = 1,
    private val apiService: ApiService,
    private val database: Database
) : RemoteMediator<Int, Result>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MICROSECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (database.getRemoteKeyDao().getCreationTime()
                ?: 0) > cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        Log.e("TAG", "load: ", )
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.e("TAG", "loadb: ${ remoteKeys?.nextKey}" )
                remoteKeys?.nextKey?.minus(1) ?: initialPage
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Log.e("TAG", "loada: ${ remoteKeys?.nextKey}" )
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Log.e("TAG", "loadc: ${ remoteKeys?.nextKey}" )
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }
        Log.e("TAG", "load: 2", )
        try {
            Log.e("TAG", "load: 1", )
            val apiResponse = apiService.getPaginatedCharacter(page = page)

            val results = apiResponse.results
            Log.e("TAG", "load: "+results.toString() )
            val endOfPaginationReached = results.isEmpty()


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getRemoteKeyDao().clearRemoteKeys()
                    database.getCharacterDao().deleteAll()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = results.map {
                    RemoteKeys(
                        characterId = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                database.getRemoteKeyDao().insertAll(remoteKeys)
                database.getCharacterDao()
                    .insertAll(results.onEachIndexed { _, character -> apiResponse.info.pages= page })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            Log.e("TAG", "IOException: "+error.message )
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            Log.e("TAG", "HttpException: "+error.message )
            return MediatorResult.Error(error)
        }
        catch (e:Exception){
            Log.e("TAG", "Exception: "+e.message )
            return MediatorResult.Error(e)
        }


    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Result>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getRemoteKeyDao().getRemoteKeyByCharacterID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            database.getRemoteKeyDao().getRemoteKeyByCharacterID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            database.getRemoteKeyDao().getRemoteKeyByCharacterID(movie.id)
        }
    }


}


