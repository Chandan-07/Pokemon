package com.example.codewithcb.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonResponseModel(
    @SerializedName("data") val data: List<PokemonDetailsModel>
)
