package com.spoton.com.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spoton.com.data.mappers.CryptoDataMapper
import com.spoton.com.domain.usecase.GetCryptoListUseCase
import com.spoton.com.domain.models.CryptoModel
import com.spoton.com.presentation.CryptoUIViewState
import com.spoton.com.remote.NetworkResults.Failure
import com.spoton.com.remote.NetworkResults.Loading
import com.spoton.com.remote.NetworkResults.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CryptoViewModel @Inject constructor(
    private val getCryptoListUseCase: GetCryptoListUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _cryptoListState =
        MutableStateFlow<CryptoUIViewState>(CryptoUIViewState.CryptoLoading)
    val cryptoListState: StateFlow<CryptoUIViewState> = _cryptoListState

    val cryptoLiveList: ArrayList<CryptoModel> = arrayListOf()

    fun getCryptoList() {
        viewModelScope.launch {
            getCryptoListUseCase.getCharacters().collect { response ->
                when (response) {
                    is Failure -> {
                        _cryptoListState.value =
                            CryptoUIViewState.CryptoFailureState(response.errorMessage)
                    }
                    is Loading -> {
                        _cryptoListState.value = CryptoUIViewState.CryptoLoading
                    }
                    is Success -> {
                        cryptoLiveList.clear()
                        cryptoLiveList.addAll(CryptoDataMapper.mapCharacter(response.data))
                        _cryptoListState.value = CryptoUIViewState.CryptoSuccessState(
                            CryptoDataMapper.mapCharacter(response.data)
                        )
                    }
                }
            }
        }
    }

    fun searchCryptoName(query: String) {
        if (query.isNotEmpty() && query.length > 2) {
            val searchList: ArrayList<CryptoModel> = arrayListOf()
            if (cryptoLiveList.isNotEmpty()) {
                for (item in cryptoLiveList) {
                    if (item.name?.lowercase()?.contains(query.lowercase()) == true) {
                        searchList.add(item)
                    }
                }
            }
            _cryptoListState.value = CryptoUIViewState.CryptoSuccessState(searchList)
        } else {
            _cryptoListState.value = CryptoUIViewState.CryptoSuccessState(cryptoLiveList)

        }

    }
}