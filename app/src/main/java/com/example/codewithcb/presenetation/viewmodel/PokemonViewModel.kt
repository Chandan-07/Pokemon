package com.example.codewithcb.presenetation.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codewithcb.domain.GetPokemonListRepository
import com.example.codewithcb.domain.models.CharacterModel
import com.example.codewithcb.remote.NetworkResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PokemonViewModel @Inject constructor(
    private val getCharacterRepository: GetPokemonListRepository
) : ViewModel() {


    val _gamesState = MutableStateFlow<NetworkResults<List<CharacterModel>>>(
        NetworkResults.Success(arrayListOf())
    )
    val gamesState: StateFlow<NetworkResults<List<CharacterModel>>> = _gamesState

    var pokemonList = arrayListOf<CharacterModel>()

    init {
        getDetailGames()
    }

    fun getDetailGames() {
        viewModelScope.launch {
            getCharacterRepository.getCharacters().collect { response ->
                _gamesState.value = response
                Log.d("viewmodelcompose", "getDetailGames: "+response.toString())
            }
        }
    }
}