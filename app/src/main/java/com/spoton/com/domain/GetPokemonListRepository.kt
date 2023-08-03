package com.spoton.com.domain


import com.spoton.com.remote.models.CryptoResponseModel

interface GetCryptoListRepository {
    suspend fun getCryptoList(): CryptoResponseModel
}