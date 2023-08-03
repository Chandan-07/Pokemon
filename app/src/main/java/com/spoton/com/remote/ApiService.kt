package com.spoton.com.remote

import com.spoton.com.remote.models.CryptoResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/assets")
    suspend fun getCryptoData(
        @Query("limit") pageSize: Int,
        @Query("offset")offset: Int): CryptoResponseModel
}