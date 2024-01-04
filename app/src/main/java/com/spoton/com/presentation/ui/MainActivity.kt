package com.spoton.com.presentation.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spoton.com.R
import com.spoton.com.presentation.ui.gallery.VideoEditActivity

class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HOMESCREEN()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun HOMESCREEN() {
         val galleryLauncher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->
             val intent = Intent(this, VideoEditActivity::class.java)
             val bundle = Bundle()
             // Put parcelable URIs into Bundle
             bundle.putParcelable("selected_uris", uri)
             intent.putExtras(bundle)
             startActivity(intent)
        }
        val context = LocalContext.current
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
                    ).size(100.dp).clickable {
                        galleryLauncher.launch("video/*")
                    }) {
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
                    Box(modifier = Modifier.padding(start = 20.dp).border(
                        width = 2.dp,
                        color = colorResource(id =  R . color . primary_color_transient),
                        shape = RoundedCornerShape(10.dp)
                    ).size(100.dp)) {
                        Image(
                            painterResource(id = R.drawable.ic_gallery),
                            contentDescription = "", modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        })
    }
}