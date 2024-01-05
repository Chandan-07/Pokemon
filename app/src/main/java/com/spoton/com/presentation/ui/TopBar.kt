package com.spoton.com.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.spoton.com.R

@OptIn(ExperimentalMaterial3Api::class) @Composable
fun ToolBarWithTitel() {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name), fontSize = 20.sp, style = TextStyle(
                    color = colorResource(id = R.color.white), fontWeight = FontWeight.Bold
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.black)),
        modifier = Modifier.background(colorResource(id = R.color.white))
    )
}