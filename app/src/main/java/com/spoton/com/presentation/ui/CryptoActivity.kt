package com.spoton.com.presentation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.spoton.com.R
import com.spoton.com.R.color
import com.spoton.com.presentation.viewmodel.CryptoViewModel
import com.spoton.com.domain.models.CryptoModel
import com.spoton.com.presentation.CryptoUIViewState
import com.spoton.com.remote.NetworkResults
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoActivity : ComponentActivity() {
    private val CryptoMainList: ArrayList<CryptoModel> = arrayListOf()
    var isSearched = false
    lateinit var viewModelCompose: CryptoViewModel
    val scope = MainScope()
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainCryptoScreen()
        }
    }

    @Composable fun MainCryptoScreen() {
        val navController = rememberNavController()
        viewModelCompose = viewModel()
        viewModelCompose.getCryptoList()
        job = scope.launch {
            while(true) {
              viewModelCompose.getCryptoList()
                delay(10000)
            }
        }
        NavHost(navController = navController, startDestination = "FirstScreen") {
            composable("FirstScreen") {
                CryptoFragment(navigation = navController, viewModelCompose)
            }
        }
    }

    @Composable
    fun CryptoFragment(navigation: NavController, viewModelCompose: CryptoViewModel) {

        val isRefreshing = viewModelCompose.isRefreshing.collectAsState().value

        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

        SwipeRefresh(state = swipeRefreshState,
            onRefresh = viewModelCompose::getCryptoList,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Scaffold(topBar = {
                TopAppBar()
            }, content = {
                when (val response = viewModelCompose.cryptoListState.collectAsState().value) {
                    is CryptoUIViewState.CryptoLoading -> {
                        CircularProgressBar()
                    }

                    is CryptoUIViewState.CryptoFailureState -> {
                        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    }

                    is CryptoUIViewState.CryptoSuccessState -> {
                        if (!isSearched) {
                            CryptoMainList.clear()
                            CryptoMainList.addAll(response.cryptoList)
                        }
                        CryptoList(response.cryptoList, navigation)
                    }
                    else -> {
                        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }



    @Composable
    private fun CircularProgressBar() {
        val strokeWidth = 5.dp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.black_background)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = colorResource(id = color.green),
                strokeWidth = strokeWidth
            )
        }
    }

    @Composable fun CryptoList(
        itemlist: List<CryptoModel>, navigation: NavController
    ) {
        Surface(
            modifier = Modifier.padding(top = 55.dp)
        ) {
            LazyColumn(modifier = Modifier.background(color = colorResource(id = R.color.grey))) {
                item {
                    SearchBar()
                }
                item {
                    CryptoHeader()
                }
                itemsIndexed(itemlist) { _index, item ->
                    CryptoCards(_index, item, navigation)
                }
            }
        }
    }

    @Composable fun SearchBar() {
        var text by remember { mutableStateOf("") }
        Row(modifier = Modifier.background(color = Color.Black)) {
            TextField(
                value = text,
                onValueChange = {
                    isSearched = true
                    text = it
                    viewModelCompose.searchCryptoName(text)
                },
                label = { Text("Search Crypto") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .padding(top = 5.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
            )

        }

    }
}
