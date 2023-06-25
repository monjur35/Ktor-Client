package com.example.ktorclient.hilt

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ktorclient.dao.CharactersDao
import com.example.ktorclient.dao.RemoteKeysDao
import com.example.ktorclient.netWork.ApiService
import com.example.ktorclient.netWork.ApiServiceImpl
import com.example.ktorclient.response.allCharacterResponse.RemoteKeys
import com.example.ktorclient.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TIME_OUT = 10_000

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                   ignoreUnknownKeys = true
                })
            }


            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Logger Ktor =>", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("TAG_HTTP_STATUS_LOGGER", "${response.status.value}")
                }
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            engine {
                requestTimeout = TIME_OUT.toLong()
            }
        }
    }

    @Provides
    fun provideApiService(httpClient: HttpClient): ApiService {
        return ApiServiceImpl(httpClient)
    }


    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context):Database=
        Room.databaseBuilder(context,Database::class.java,"character_DB")
            .addMigrations(migration1to2)
            .build()

    private val migration1to2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE tblCharacters RENAME COLUMN origin_url TO origin_name")
            database.execSQL("ALTER TABLE tblCharacters RENAME COLUMN location_url TO location_name")

            // Add new columns
            database.execSQL("ALTER TABLE tblCharacters ADD COLUMN page INTEGER DEFAULT 0")
        }
    }




    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: Database):RemoteKeysDao=database.getRemoteKeyDao()

    @Singleton
    @Provides
    fun provideCharactersDao(database: Database):CharactersDao=database.getCharacterDao()
}