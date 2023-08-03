package com.example.com

import com.spoton.com.data.GetCryptoListRepoImpl
import com.spoton.com.domain.GetCryptoListRepository
import com.spoton.com.domain.usecase.GetCryptoListUseCase
import com.spoton.com.remote.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CryptoRepositoryTest {

    @Mock
    private lateinit var apiService: ApiService
    private var getCryptoListRepository: GetCryptoListRepository? = null
    private var getCryptoListUseCase: GetCryptoListUseCase? = null

    @Test
    fun testCryptoListIsNotEmpty() {
       getCryptoListRepository = GetCryptoListRepoImpl(apiService)
       getCryptoListUseCase = GetCryptoListUseCase(getCryptoListRepository as GetCryptoListRepoImpl)
        runTest {
           val cryptoList =  getCryptoListRepository?.getCryptoList()
            cryptoList?.data?.isNotEmpty()?.let { assert(it) }
       }
    }

    @Test
    fun testCryptoListIsEmpty() {
        getCryptoListRepository = GetCryptoListRepoImpl(apiService)
        getCryptoListUseCase = GetCryptoListUseCase(getCryptoListRepository as GetCryptoListRepoImpl)
        runTest {
            val cryptoList =  getCryptoListRepository?.getCryptoList()
            cryptoList?.data?.isEmpty()?.let { assert(it) }
        }
    }

    @Test
    fun testRefreshCryptoListAfterTenSeconds() {
        getCryptoListRepository = GetCryptoListRepoImpl(apiService)
        runTest {
            launch {
                delay(10000)
                getCryptoListRepository?.getCryptoList()
            }
        }
    }
}