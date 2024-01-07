package com.spoton.com.presentation.ui.gallery

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.spoton.com.R
import com.spoton.com.presentation.ui.AppConstants
import com.spoton.com.presentation.ui.ToolBarWithTitel
import com.spoton.com.presentation.ui.gallery.model.VideoActions

class VideoEditActivity: AppCompatActivity() {
    private lateinit var videoPlayManager: VideoPlayManager
    private var currentVideoIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        videoPlayManager = VideoPlayManager(this, object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                if (playbackState == SimpleExoPlayer.STATE_ENDED) {
                    // Handle logic to move to the next video
                    // ...
                }
            }
        })
        setContent {
            val bundle = intent.extras
            val selectedUris = bundle?.getParcelableArrayList<Uri>(AppConstants.SELECTED_URIS)
            selectedUris?.let { PreviewScreen(it, onBackPressedDispatcher) }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PreviewScreen(uris: List<Uri>, onBackPressedDispatcher: OnBackPressedDispatcher) {
        val context = LocalContext.current
            val playerListener = remember { object : Player.Listener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        val hasNextVideo = false
                        if (hasNextVideo) {
                            currentVideoIndex++
                            val hasNextVideo = currentVideoIndex < uris.size
                            if (hasNextVideo) {
                                val nextVideoUri = uris[currentVideoIndex]
                                videoPlayManager.playVideos(listOf(nextVideoUri))
                            }
                        } else {
                            // Stop playback if there are no more videos in the playlist
                            videoPlayManager.stop()
                        }
                    }
                }
            }}
        var exoPlayer: SimpleExoPlayer by remember { mutableStateOf(createExoPlayer(context, uris, playerListener)) }
        var actionButton by remember { mutableStateOf( R.drawable.ic_play) }

        BackHandler {
                exoPlayer.release()
            }

            Column {   TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp,
                        style = TextStyle(
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        exoPlayer.release()
                        finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
                Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
                    AndroidView(
                        factory = {
                            PlayerView(context).apply {
                                player = exoPlayer
                                player
                            }.also {
                                it .useController = false
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp),
                        update = {
                            it.player = exoPlayer
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                if (exoPlayer.isPlaying) {
                                    exoPlayer.pause()
                                    actionButton = R.drawable.ic_pause
                                } else {
                                    exoPlayer.play()
                                    actionButton = R.drawable.ic_play
                                }
                            }
                        ) {
                            actionButton = if (exoPlayer.isPlaying) {
                                R.drawable.ic_pause
                            } else {
                                R.drawable.ic_play
                            }
                            Icon(painter = painterResource(id =actionButton), contentDescription = "")
                        }
                    }
                }
                val drawableList = listOf(
                    VideoActions(R.drawable.ic_crop, "crop"),
                    VideoActions(R.drawable.ic_music, "music"),
                    VideoActions(R.drawable.ic_stickers, "stickers"),
                    VideoActions(R.drawable.ic_text, "text"),
                    VideoActions(R.drawable.ic_filter, "filters"),
                    VideoActions(R.drawable.ic_file, "files"),
                    VideoActions(R.drawable.ic_precut, "precut"),
                    VideoActions(R.drawable.ic_split, "split"),
                    VideoActions(R.drawable.baseline_delete_24, "delete"),
                    // Add more drawable resource IDs as needed
                )
                LazyRow(
                    content = {
                        items(drawableList) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)) {
                                Icon(
                                    painter = painterResource(id = it.icon),
                                    contentDescription = null,

                                )
                                Text(text = it.title)
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
        }
    }

    private fun createExoPlayer(context: Context, uris: List<Uri>, playerListener: Player.Listener): SimpleExoPlayer {
        val player: SimpleExoPlayer by lazy {
            SimpleExoPlayer.Builder(context).build().apply {
                addListener(playerListener)
            }
        }
        val mediaItems = uris.map { MediaItem.fromUri(it) }
        player.setMediaItems(mediaItems)
        player.prepare()
        return player
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onBackPressed() {
        super.getOnBackInvokedDispatcher()
    }
}