package com.example.codewithcb.presenetation.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.codewithcb.R
import com.example.codewithcb.R.color
import com.example.codewithcb.presenetation.PokemonCards
import com.example.codewithcb.presenetation.utils.SortTypes.SORT_BY_HP
import com.example.codewithcb.presenetation.utils.SortTypes.SORT_BY_LEVEL
import com.example.codewithcb.presenetation.utils.Routes
import com.example.codewithcb.presenetation.utils.SortTypes
import com.example.codewithcb.presenetation.viewmodel.PokemonViewModel
import com.example.codewithcb.domain.models.CharacterModel
import com.example.codewithcb.remote.NetworkResults
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {
    private val pokemonMainList : ArrayList<CharacterModel> = arrayListOf()
    var isSearched = false
    lateinit var  viewModelCompose: PokemonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationExampleTheme()
        }
    }
    @Composable
    fun NavigationExampleTheme() {
        val navController = rememberNavController()
        viewModelCompose = viewModel()
        NavHost(navController = navController, startDestination = "FirstScreen") {
            composable("FirstScreen") {
                PokemonFragmnet(navigation = navController, viewModelCompose)
            }
            composable(
                Routes.SECOND_SCREEN+"/{pokemonDetails}",
                arguments = listOf(navArgument("pokemonDetails") {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val response = backStackEntry.arguments?.getString("pokemonDetails")
                val gson = Gson()
                val characterModel: CharacterModel = gson.fromJson(response, CharacterModel::class.java)
                PokemonDetailsScreen(navigation = navController, characterModel)
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class) @Composable
    fun PokemonFragmnet(navigation: NavController, viewModelCompose: PokemonViewModel) {
        Scaffold(  topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pokemon List", fontSize = 20.sp, style = TextStyle(color = colorResource(id = color.white),
                        fontWeight = FontWeight.Bold))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.primary_color))
            )
        } ,content = {
            when (val response = viewModelCompose.gamesState.collectAsState().value) {
                is NetworkResults.Loading -> {
                    // binding.progressbar.isVisible = true

                }

                is NetworkResults.Failure -> {
                    Toast.makeText(this, response.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is NetworkResults.Success -> {
                    if (!isSearched) {
                        pokemonMainList.clear()
                        pokemonMainList.addAll(response.data)
                    }
                    PokemonList(response.data, navigation)

                }
                else -> {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



    @OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class) @Composable
    fun PokemonDetailsScreen(navigation: NavController, characterModel: CharacterModel) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Pokemon Details", style = TextStyle(color = colorResource(id = color.black)))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            super.onBackPressedDispatcher.onBackPressed()
                        }) {
                            Icon(Filled.ArrowBack,"")
                        }
                    },
                )
            }, content = {

                Column(modifier = Modifier.padding(start = 80.dp,
                    end = 20.dp)) {
                    GlideImage(
                        model = characterModel.images.large,
                        contentDescription = null,
                        modifier = Modifier
                            .height(500.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Row() {
                            Text(text = "Name - ",fontSize = 16.sp, style = TextStyle(color = colorResource(id = color.grey)))
                            Text(text = characterModel.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                        if (characterModel.level> 0){
                            Row(modifier = Modifier.padding(top = 3.dp)) {
                                Text(text = "Level - ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                                Text(text = characterModel.level.toString())
                            }
                        }

                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = characterModel.hp,fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.red)),
                                fontWeight = Companion.Bold)
                            Text(text = "HP",fontSize = 12.sp, style = TextStyle(color = colorResource(id = color.red)),
                                modifier = Modifier.padding(start = 5.dp))


                        }
                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = "SubType - ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                            Text(text = characterModel.subtypes.toString().replace(
                                "[","").replace("]",""), fontSize = 14.sp,
                                style = TextStyle(color = colorResource(id = color.black)))
                        }
                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = "Weaknessess : ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))

                            characterModel.weaknesses?.let{
                                LazyRow(content = {
                                    items(it) {
                                        Text(text = it.type+" : "+it.value, fontSize = 14.sp,
                                            style = TextStyle(color = colorResource(id = color.black)))

                                    }
                                })
                            }

                        }
                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = "Attacks : ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                            characterModel.attacks?.let{
                                LazyRow(content = {
                                    items(it) {
                                        Text(text = it.name+",", fontSize = 14.sp,
                                            style = TextStyle(color = colorResource(id = color.black)))

                                    }
                                })
                            }

                        }
                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = "Resistances : ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                            characterModel.resistances?.let {
                                LazyRow(content = {
                                    items(it) {
                                        Text(text = it.type+" : "+it.value, fontSize = 14.sp,
                                            style = TextStyle(color = colorResource(id = color.black)))

                                    }
                                })
                            }

                        }
                        Row(modifier = Modifier.padding(top = 3.dp)) {
                            Text(text = "Abilities : ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))

                            characterModel.abilities?.let {
                                LazyRow(content = {
                                    items(it) {
                                        Text(text = it.name +", ", fontSize = 14.sp,
                                            style = TextStyle(color = colorResource(id = color.black)))

                                    }
                                })
                            }

                        }
                    }


                }
            })
    }


   @Composable
    fun PokemonList(
        itemlist: List<CharacterModel>,
        navigation: NavController
    ) {
        Surface(modifier = Modifier
            .padding(top = 55.dp)
           ) {

            LazyColumn(modifier = Modifier.background(color = colorResource(id = R.color.grey_light))) {
                item {
                    SearchBar()
                }
                itemsIndexed(itemlist) { _index , item->
                    PokemonCards(item, navigation)
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class) @Composable
    fun SearchBar() {
        val openDialog = remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }
        Row(modifier = Modifier.background(color = Color.White)) {
            TextField(
                value = text,
                onValueChange = {
                    isSearched = true
                    text = it
                    searchPokemon(text)
                },
                label = { Text("Search Pokemon") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                modifier = Modifier
                    .width(310.dp)
                    .height(55.dp)
                    .padding(top = 5.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            )
            Text(text = "Sort",fontSize = 16.sp, style = TextStyle(color = colorResource(id = color.dark_green),
                fontWeight = Companion.Bold), modifier = Modifier
                .padding(start = 25.dp, top = 10.dp)
                .fillMaxWidth()
                .align(CenterVertically)
                .clickable {
                    openDialog.value = true
                })

        }


        if (openDialog.value) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                }
            ) {
                val radioOptions = listOf("Sort By HP", "Sort By Level")
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[1]) }
                Column(
                    modifier = Modifier
                        .padding(top = 30.dp, bottom = 30.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(corner = CornerSize(10.dp))
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Column {
                        radioOptions.forEach { text ->
                            Row(
                                Modifier
                                    .selectable(selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) })
                                    .padding(horizontal = 5.dp)
                            ) {
                                RadioButton(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        if (openDialog.value) {
                                            onOptionSelected(text)
                                            if(text == "Sort By HP") {
                                                sort(SORT_BY_HP)
                                            } else {
                                                sort(SORT_BY_LEVEL)
                                            }
                                        }
                                    }
                                )
                                Text(
                                    text = text,
                                    modifier = Modifier.padding(start = 10.dp, top = 15.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    private fun searchPokemon(query: String) {
        if (query.isNotEmpty() && query.length> 2) {
            val searchList: ArrayList<CharacterModel> = arrayListOf()
            if (pokemonMainList.isNotEmpty()) {
                for(item in pokemonMainList) {
                    if ( item.name.lowercase().contains(query.lowercase())){
                        searchList.add(item)
                    }

                }
            }
            viewModelCompose._gamesState.value = NetworkResults.Success(searchList)
        } else {
            viewModelCompose._gamesState.value = NetworkResults.Success(pokemonMainList)

        }
        
    }

    private fun sort(type: SortTypes) {
        val sortedList : ArrayList<CharacterModel> = arrayListOf()
        sortedList.clear()
        sortedList.addAll(pokemonMainList)
        isSearched = true

        when(type) {
            SORT_BY_HP -> { sortedList.sortWith(Comparator { a: CharacterModel, b: CharacterModel ->
                if (a.hp < b.hp) -1 else if (a.hp === b.hp) 0 else 1
            })}
            SORT_BY_LEVEL -> { sortedList.sortWith(Comparator { a: CharacterModel, b: CharacterModel ->
                if (a.level < b.level) 1 else if (a.level == b.level) 0 else -1
            })}

        }
        viewModelCompose._gamesState.value = NetworkResults.Success(sortedList)


    }


}
