package com.example.codewithcb.data

import com.example.codewithcb.data.mappers.PokemonMapper
import com.example.codewithcb.domain.GetPokemonListRepository
import com.example.codewithcb.remote.ApiService
import com.example.codewithcb.remote.NetworkResults
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListRepoImpl @Inject constructor (private val apiService: ApiService): GetPokemonListRepository {
    override suspend fun getCharacters() = flow {
        emit(NetworkResults.Loading(true))
        val response = apiService.getCharacters(20)
        emit(NetworkResults.Success(PokemonMapper.mapCharacter(response)))
    }.catch { e ->
        emit(NetworkResults.Failure(e.message ?: "Unknown Error"))
    }
}