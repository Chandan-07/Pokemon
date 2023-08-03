package com.spoton.com.remote.models

import com.google.gson.annotations.SerializedName

data class CryptoResponseModel(
    @SerializedName("data") val data: List<CryptoDetailsModel>
)
