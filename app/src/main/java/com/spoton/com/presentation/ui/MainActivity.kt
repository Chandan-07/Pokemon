package com.spoton.com.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spoton.com.R

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HOMESCREEN()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HOMESCREEN() {
        Scaffold(topBar = {
            TopAppBar()
        }, content = {
            Column(modifier = Modifier.padding(top = 80.dp)) {
                Text(
                    text = stringResource(id = R.string.create_new), fontSize = 20.sp, style = TextStyle(
                        color = colorResource(id = R.color.black), fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(20.dp)
                )
                Row(modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    ) {
                    Box(modifier = Modifier.border(
                        width = 2.dp,
                        color = colorResource(id =  R . color . primary_color_transient),
                        shape = RoundedCornerShape(10.dp)
                    ).size(100.dp)) {
                        Image(
                            painterResource(id = R.drawable.ic_video),
                            contentDescription = "", modifier = Modifier.align(Alignment.Center))
                    }
                    Box(modifier = Modifier.padding(start = 20.dp).border(
                            width = 2.dp,
                        color = colorResource(id =  R . color . primary_color_transient),
                        shape = RoundedCornerShape(10.dp)
                    ).size(100.dp)) {
                        Image(
                            painterResource(id = R.drawable.ic_collage),
                            contentDescription = "", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        })
    }
}