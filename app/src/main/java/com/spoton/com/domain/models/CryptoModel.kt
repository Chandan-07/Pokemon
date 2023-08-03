package com.spoton.com.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CryptoModel(
    val name: String?,
    val id: String?,
    val symbol: String?,
    val rank: String?,
    val supply: String?,
    val maxSupply: String?,
    val marketCapUsd: String?,
    val volumeUsd24Hr: String?,
    val changePercent24Hr: String?,
    val vwap24Hr: String?,
    val explorer: String?,
    val priceUsd: String?
) : Parcelable
