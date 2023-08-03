package com.spoton.com.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spoton.com.R.color
import com.spoton.com.R.string

@Composable fun CryptoHeader() {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .background(
                colorResource(id = color.black_background)
            ),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "#",
            fontSize = 14.sp,
            style = TextStyle(color = colorResource(id = color.grey_light)),
            modifier = Modifier
                .weight(0.5f)
                .padding(
                    start = 8.dp, top = 10.dp, bottom = 10.dp
                )
        )
        Text(
            text = stringResource(id = string.name),
            fontSize = 14.sp,
            style = TextStyle(color = colorResource(id = color.grey_light)),
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, top = 10.dp, bottom = 10.dp)
        )


        Text(
            text = "24Hr",
            fontSize = 14.sp,
            style = TextStyle(color = colorResource(id = color.grey_light)),
            modifier = Modifier
                .weight(1f)
                .padding(start = 25.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
        )


        Text(
            text = stringResource(id = string.price),
            fontSize = 14.sp,
            style = TextStyle(color = colorResource(id = color.grey_light)),
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
        )
    }
}