package com.example.com

import com.spoton.com.data.GetCryptoListRepoImpl
import com.spoton.com.domain.usecase.GetCryptoListUseCase
import com.spoton.com.presentation.viewmodel.CryptoViewModel
import com.spoton.com.remote.ApiService
import org.junit.Before
import org.junit.Test

class CryptoViewModelTest {

    private lateinit var apiService: ApiService
    private lateinit var viewModel: CryptoViewModel
    @Before
    fun initViewModel() {
        val cryptoListRepoImpl = GetCryptoListRepoImpl(apiService)
        val cryptoListUseCase = GetCryptoListUseCase(cryptoListRepoImpl)
        viewModel = CryptoViewModel(
            cryptoListUseCase
        )
    }

    @Test
    fun testSearchedCryptoIsNotEmpty() {
        viewModel.searchCryptoName("Bitcoin")
       assert(viewModel.cryptoLiveList.isNotEmpty())
    }
}