package com.example.codewithcb.presenetation


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.codewithcb.R.color
import com.example.codewithcb.presenetation.utils.Routes
import com.example.codewithcb.domain.models.CharacterModel
import com.google.gson.Gson

@OptIn(ExperimentalGlideComposeApi::class) @Composable
fun PokemonCards(item: CharacterModel, navigation: NavController) {
    Row(modifier = Modifier
        .padding(top = 1.dp, bottom = 1.dp)
        .background(
            Color.White
        ).fillMaxWidth(1F)
            .clickable {
            val json = Uri.encode(Gson().toJson(item))
            navigation.navigate("${Routes.SECOND_SCREEN}/$json")
        }) {
        GlideImage(
            model = item.images.large,
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .padding(top = 20.dp, bottom = 20.dp)
                .clickable {
                    val json = Uri.encode(Gson().toJson(item))
                    navigation.navigate("${Routes.SECOND_SCREEN}/$json")
                },
            contentScale = ContentScale.Inside
        )

        Column(modifier = Modifier.padding(top = 20.dp)) {
            Row() {
                Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            if (item.level> 0){
                Row(modifier = Modifier.padding(top = 3.dp)) {
                    Text(text = "Level - ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                    Text(text = item.level.toString())
                }
            }

            Row(modifier = Modifier.padding(top = 3.dp)) {
                Text(text = item.hp,fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.red)),
                    fontWeight = FontWeight.Bold)
                Text(text = "HP",fontSize = 12.sp, style = TextStyle(color = colorResource(id = color.red)),
                    modifier = Modifier.padding(start = 5.dp))


            }
            Row(modifier = Modifier.padding(top = 3.dp)) {
                Text(text = "Type - ",fontSize = 14.sp, style = TextStyle(color = colorResource(id = color.grey)))
                Text(text = item.types.toString().replace(
                    "[","").replace("]",""), fontSize = 14.sp,
                    style = TextStyle(color = colorResource(id = color.grey))
                )
            }
        }


    }
}

