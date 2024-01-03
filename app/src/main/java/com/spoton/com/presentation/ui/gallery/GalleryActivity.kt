package com.spoton.com.presentation.ui.gallery

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class GalleryActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalleryScreen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun GalleryScreen() {
        val context = LocalContext.current

        // Get a list of URIs for videos (you might want to filter images here)
        val videosUris: List<Uri> = getAllVideosFromGallery(context)

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(items = videosUris) { videoUri->
                VideoItem(videoUri)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    @Composable
    fun VideoItem(videoUri: Uri) {
        val context = LocalContext.current

        val contentResolver = context.contentResolver
        val bitmap = contentResolver.loadThumbnail(videoUri, Size(128, 128), null)

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    // Open the selected video when clicked
                    context.startActivity(Intent(Intent.ACTION_VIEW, videoUri))
                }
        )
    }

    // Function to get URIs of all videos from the device's gallery
    private fun getAllVideosFromGallery(context: Context): List<Uri> {
        val videoUris = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Video.Media._ID)
        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO}"
        val queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        context.contentResolver.query(
            queryUri,
            projection,
            selection,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                videoUris.add(contentUri)
            }
        }
        return videoUris
    }
}
