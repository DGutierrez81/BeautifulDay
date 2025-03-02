package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable que muestra la pantalla de registro de imágenes.
 *
 * @param navController Controlador de navegación para manejar la navegación entre destinos.
 * @param viewmodel ViewModel de la aplicación que contiene la lógica de negocio y los estados relacionados con la vista.
 * @param context Actividad principal para acceder al contexto de la aplicación.
 * @param email Correo electrónico del usuario.
 * @param numb Número de teléfono del usuario.
 */
@Composable
fun RegistroM(navController: NavController, viewmodel: ViewmodelAplication, context: ComponentActivity, email: String, numb: String){
    // Estado del UI
    val uiState by viewmodel.uiState.collectAsState()
    // Usuario
    val user = viewmodel.user
    // Nombre de la imagen seleccionada
    var selectedImageName by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    // Nombre del usuario
    var name by rememberSaveable {
        mutableStateOf("")
    }

    // Cargar las imágenes del usuario
    LaunchedEffect(Unit){
        viewmodel.getAllImages(email)
    }

    // Mostrar la lista de imágenes en una cuadrícula
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.background(color = colorResource(id = R.color.electricBlue))){
        items(uiState.images){ image ->
            Column(
                modifier = Modifier
                    .clickable { selectedImageName = image.toUri().lastPathSegment.toString() }
                    .background(color = colorResource(id = R.color.selectiveYellow))
                    .padding(5.dp)
            ) {
                // Tarjeta de imagen
                Card(
                    modifier = Modifier
                        .border(BorderStroke(3.dp, color = colorResource(id = R.color.silver)))
                        .padding(2.dp)
                ) {
                    AsyncImage(
                        model = image,
                        contentDescription = "images"
                    )
                }
                // Nombre de la imagen
                Text(text = image.toUri().lastPathSegment.toString(), color = colorResource(id = R.color.paynesGray), textAlign = TextAlign.Center)
            }
        }
    }

    // Confirmar eliminación de la imagen seleccionada
    selectedImageName?.let { imageName ->
        AlertDialog(
            onDismissRequest = { selectedImageName = null },
            title = { Text(text = "¿Deseas eliminar esta imagen?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Llama a la función de tu ViewModel para eliminar la imagen
                        viewmodel.deleteImage(imageName, context)
                        navController.navigate("camera/${user.email}?numb=$numb")
                        //selectedImageName = null
                    }
                ) {
                    Text(text = "Eliminar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { selectedImageName = null }
                ) {
                    Text(text = "Cancelar")
                }
            }
        )
    }
}
