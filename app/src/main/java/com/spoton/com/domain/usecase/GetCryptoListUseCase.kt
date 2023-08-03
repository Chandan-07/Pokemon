package com.spoton.com.domain.usecase

import com.spoton.com.data.GetCryptoListRepoImpl
import com.spoton.com.remote.NetworkResults
import com.spoton.com.remote.NetworkResults.Loading
import com.spoton.com.remote.NetworkResults.Success
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCryptoListUseCase  @Inject constructor (private val getCryptoListRepository: GetCryptoListRepoImpl){
    suspend fun getCharacters() = flow {
        emit(Loading(true))
        val response = getCryptoListRepository.getCryptoList()
        emit(Success(response))
    }.catch { e ->
        emit(NetworkResults.Failure(e.message ?: "Unknown Error"))
    }
}