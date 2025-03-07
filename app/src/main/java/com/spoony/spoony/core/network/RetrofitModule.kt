package com.spoony.spoony.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.spoony.spoony.BuildConfig.BASE_URL
import com.spoony.spoony.core.util.extension.isJsonArray
import com.spoony.spoony.core.util.extension.isJsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun provideLoggingInterceptor() = HttpLoggingInterceptor { message ->
        val jsonMessage = when {
            message.contains("Content-Disposition: form-data;") -> {
                val jsonStart = message.indexOf("{")
                val jsonEnd = message.lastIndexOf("}")

                if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
                    message.substring(jsonStart, jsonEnd + 1)
                } else {
                    message
                }
            }

            else -> message
        }

        when {
            jsonMessage.isJsonObject() ->
                Timber.tag("okhttp").d(JSONObject(jsonMessage).toString(4))

            jsonMessage.isJsonArray() ->
                Timber.tag("okhttp").d(JSONObject(jsonMessage).toString(4))

            else -> {
                Timber.tag("okhttp").d("CONNECTION INFO -> $jsonMessage")
            }
        }
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        factory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()
}
