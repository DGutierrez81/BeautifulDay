package com.project.beautifulday.Components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable que muestra Google Maps con la ubicación guardada.
 *
 * @param viewModel ViewModel de la aplicación.
 * @param context Actividad actual.
 */
@Composable
fun MyGoogleMaps(viewModel: ViewmodelAplication, context: ComponentActivity) {

    // Obtiene la ubicación guardada del ViewModel
    val savedLocation by viewModel.localizacion.collectAsState()

    // Muestra Google Maps con la ubicación guardada al iniciar
    LaunchedEffect(key1 = 1) {
        savedLocation?.let {
            // Crea el URI para abrir Google Maps con la ubicación guardada
            val gmmIntentUri = Uri.parse("geo:${it.latitude},${it.longitude}?q=${it.latitude},${it.longitude}(Ubicación+Guardada)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            // Inicia la actividad de Google Maps
            context.startActivity(mapIntent)
        } ?: run {
            // Muestra un mensaje si la ubicación guardada no está disponible
            Toast.makeText(context, "Ubicación guardada no disponible", Toast.LENGTH_SHORT).show()
        }
    }
}
