package com.example.codewithcb.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailsModel(
    @SerializedName("name") val name: String,
    @SerializedName("images") val images: Image,
    @SerializedName("types") val types: ArrayList<String>,
    @SerializedName("hp") val hp: String,
    @SerializedName("subtypes") val subtypes: ArrayList<String>? = null,
    @SerializedName("resistances") val resistances: ArrayList<Weakness>? = null,
    @SerializedName("weaknesses") val weaknesses: ArrayList<Weakness>? = null,
    @SerializedName("attacks") val attacks: ArrayList<Attacks>? = null,
    @SerializedName("level") val level: Int,
    @SerializedName("abilities") val abilities: ArrayList<Attacks>? =null,

    )

data class Image(
    @SerializedName("small") val small: String,
    @SerializedName("large") val large: String
)

data class Attacks(
    @SerializedName("name") val name: String?
)

data class Weakness(
    @SerializedName("type") val type: String?,
    @SerializedName("value") val value: String?
)