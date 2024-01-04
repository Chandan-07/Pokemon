package com.spoton.com.presentation.ui.gallery

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.spoton.com.R
import com.spoton.com.presentation.ui.TopAppBar

class VideoEditActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val bundle = intent.extras
            val selectedUris = bundle?.getParcelable<Uri>("selected_uris")
            selectedUris?.let { PreviewScreen(it.toString()) }
        }
    }

    @Composable
    fun PreviewScreen(videoUri: String) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        var exoPlayer: SimpleExoPlayer by remember { mutableStateOf(createExoPlayer(context, videoUri)) }

        BackHandler {
            exoPlayer.release()
        }

        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxWidth().height(500.dp).padding(20.dp),
            update = {
                it.player = exoPlayer
            }
        )
    }
    private fun createExoPlayer(context: Context, videoUri: String): SimpleExoPlayer {
        val player = SimpleExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        return player
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onBackPressed() {
        super.getOnBackInvokedDispatcher()
    }
}