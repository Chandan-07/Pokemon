package com.example.codewithcb.domain


import com.example.codewithcb.domain.models.CharacterModel
import com.example.codewithcb.remote.NetworkResults
import kotlinx.coroutines.flow.Flow

interface GetPokemonListRepository {

    suspend fun getCharacters(): Flow<NetworkResults<List<CharacterModel>>>
}