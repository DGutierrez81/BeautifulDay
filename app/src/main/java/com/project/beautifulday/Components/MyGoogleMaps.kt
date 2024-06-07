package com.project.beautifulday.Components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun MyGoogleMaps(viewModel: ViewmodelAplication, context: ComponentActivity) {

    val savedLocation  by viewModel.localizacion.collectAsState()

    LaunchedEffect(key1 = 1){
        savedLocation?.let {
            val gmmIntentUri = Uri.parse("geo:${it.latitude},${it.longitude}?q=${it.latitude},${it.longitude}(Ubicación+Guardada)")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)
        } ?: run {
            Toast.makeText(context, "Ubicación guardada no disponible", Toast.LENGTH_SHORT).show()
        }
    }

/*
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = {
                savedLocation?.let {
                    val gmmIntentUri = Uri.parse("geo:${it.latitude},${it.longitude}?q=${it.latitude},${it.longitude}(Ubicación+Guardada)")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                } ?: run {
                    Toast.makeText(context, "Ubicación guardada no disponible", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Abrir Google Maps")
        }
    }

 */
}