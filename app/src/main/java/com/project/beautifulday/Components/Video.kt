package com.project.beautifulday.Components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.ViewmodelAplication

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Video(viewmodelA: ViewmodelAplication) {
    val url = viewmodelA.uriVideo
    val mediaPlayer = ExoPlayer.Builder(LocalContext.current).build()
    val media = url?.let { MediaItem.fromUri(it) }

    LaunchedEffect(media) {
        if (media != null) {
            mediaPlayer.setMediaItem(media)
            mediaPlayer.pause()
        }
        mediaPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        mediaPlayer.prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = mediaPlayer
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}
