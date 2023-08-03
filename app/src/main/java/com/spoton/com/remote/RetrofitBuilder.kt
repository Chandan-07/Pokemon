package com.spoton.com.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    private const val BASE_URL = "https://api.coincap.io/"
    private const val OK_HTTP_TIMEOUT = 60L

    @Provides fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(createOkHttpClient(createLoggingInterceptor(true)))
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    @Provides @Named("loggingInterceptor")
    fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS).build()
    }
}