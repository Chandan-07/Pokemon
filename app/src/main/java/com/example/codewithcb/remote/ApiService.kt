package com.example.codewithcb.remote

import com.example.codewithcb.remote.models.PokemonDetailsModel
import com.example.codewithcb.remote.models.PokemonResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/cards")
    suspend fun getCharacters(@Query("pageSize") pageSize: Int): PokemonResponseModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Long): PokemonDetailsModel
}