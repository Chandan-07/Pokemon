package com.spoton.com.presentation

import com.spoton.com.domain.models.CryptoModel

sealed class CryptoUIViewState {
    data class CryptoSuccessState(val cryptoList: List<CryptoModel>): CryptoUIViewState()
    data class CryptoFailureState(val message: String): CryptoUIViewState()
    object CryptoLoading: CryptoUIViewState()
}

