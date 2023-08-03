package com.spoton.com.data

import com.spoton.com.domain.GetCryptoListRepository
import com.spoton.com.remote.ApiService
import com.spoton.com.remote.models.CryptoResponseModel
import javax.inject.Inject

class GetCryptoListRepoImpl @Inject constructor (private val apiService: ApiService):
    GetCryptoListRepository {
    override suspend fun getCryptoList(): CryptoResponseModel {
        return  apiService.getCryptoData(20,0)
    }
}