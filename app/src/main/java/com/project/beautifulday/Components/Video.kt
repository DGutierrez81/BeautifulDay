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

/**
 * Composable para reproducir videos utilizando ExoPlayer.
 *
 * @param viewmodelA ViewModel de la aplicación que contiene la URI del video a reproducir.
 */
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Video(viewmodelA: ViewmodelAplication) {
    // URI del video
    val url = viewmodelA.uriVideo
    // Crear el reproductor ExoPlayer
    val mediaPlayer = ExoPlayer.Builder(LocalContext.current).build()
    // Crear el objeto MediaItem a partir de la URI del video
    val media = url?.let { MediaItem.fromUri(it) }

    // LaunchedEffect para preparar y configurar el reproductor cuando cambia la URI del video
    LaunchedEffect(media) {
        if (media != null) {
            mediaPlayer.setMediaItem(media)
            mediaPlayer.pause()
        }
        mediaPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        mediaPlayer.prepare()
    }

    // DisposableEffect para liberar los recursos del reproductor cuando el composable se desecha
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    // AndroidView que muestra el reproductor de video
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

