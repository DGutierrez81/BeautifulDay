package com.project.beautifulday.Components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable que muestra Google Maps con la ubicación guardada.
 *
 * @param viewModel ViewModel de la aplicación.
 * @param context Actividad actual.
 */
@Composable
fun MyGoogleMaps(viewModel: ViewmodelAplication, context: ComponentActivity) {
    val local = viewModel.local
    var openGoogle = viewModel.openGoogle
    if (openGoogle) {
        Dialog(onDismissRequest = { openGoogle = true }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val savedLocation = local.latitud?.let { latitud ->
                    local.longitud?.let { longitud ->
                        LatLng(latitud, longitud)
                    }
                }

                if (savedLocation != null) {
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(
                            LatLng(savedLocation.latitude, savedLocation.longitude), 14f
                        )
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .border(
                                width = 5.dp,
                                color = colorResource(id = R.color.silver)
                            ),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = LatLng(savedLocation.latitude, savedLocation.longitude)),
                            title = "Ubicación Guardada"
                        )
                    }
                } else {
                    Text(text = "Ubicación guardada no disponible")
                }

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
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.paynesGray)
                    )
                ) {
                    Text(text = "Abrir en Google Maps", color = colorResource(id = R.color.silver))
                }
                Button(
                    onClick = { viewModel.changeOpenGoogle(false) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.paynesGray)
                    )
                ) {
                    Text(text = "Cerrar", color = colorResource(id = R.color.silver))
                }
            }
        }
    }

/*
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

 */
}
