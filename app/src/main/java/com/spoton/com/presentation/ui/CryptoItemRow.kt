package com.spoton.com.presentation.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.spoton.com.R.color
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.spoton.com.domain.models.CryptoModel

@OptIn(ExperimentalGlideComposeApi::class) @Composable
fun CryptoCards(index: Int, item: CryptoModel, navigation: NavController) {
    Row(modifier = Modifier
        .padding(top = 0.6.dp, bottom = 1.dp)
        .background(
            colorResource(id = color.black_background)
        )
        .fillMaxWidth(1F)) {
        Row(modifier = Modifier.padding(top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = (index+1).toString(),fontSize = 14.sp,
                style = TextStyle(color = colorResource(id = color.white)),
                modifier = Modifier
                    .weight(0.5f)
                    .padding(
                        start = 10.dp, top = 20.dp, bottom = 20.dp
                    ))
            item.name?.let { Text(text = it,fontSize = 13.sp,
                style = TextStyle(color = colorResource(id = color.white)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 20.dp, bottom = 20.dp)) }


            var textColor: Color = Color.White
            item.changePercent24Hr?.let {
                val sign = if(it.toDouble()> 0) "+" else ""
                textColor = if(it.toDouble()> 0) colorResource(id = color.green) else colorResource(id = color.red)
                Text(text = sign+String.format("%.2f".format(it.toDouble()))+" %",fontSize = 14.sp,
                    style = TextStyle(color = textColor),
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp))
            }
            item.priceUsd?.let {
                Text(text = "$"+String.format( "%.2f".format(it.toDouble()) ),
                fontSize = 14.sp,
                style = TextStyle(color = textColor),
                modifier = Modifier
                    .weight(1f)
                    .padding(20.dp))}
        }
    }
}

