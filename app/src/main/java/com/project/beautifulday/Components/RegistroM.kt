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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun RegistroM(navController: NavController, viewmodel: ViewmodelAplication, context: ComponentActivity, email: String){
    val uiState by viewmodel.uiState.collectAsState()
    var selectedImageName by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        viewmodel.getAllImages(email)
    }


    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.background(color = colorResource(id = R.color.electricBlue))){
        items(uiState.images){ image ->
            Column(
                modifier = Modifier
                    .clickable { selectedImageName = image.toUri().lastPathSegment.toString() }
                    .background(color = colorResource(id = R.color.selectiveYellow))
                    .padding(5.dp)
            ) {
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
                Text(text = image.toUri().lastPathSegment.toString(), color = colorResource(id = R.color.paynesGray), textAlign = TextAlign.Center)

            }
        }
    }

    // Borrar la imagen seleccionada
    selectedImageName?.let { imageName ->
        AlertDialog(
            onDismissRequest = { selectedImageName = null },
            title = { Text(text = "¿Deseas eliminar esta imagen?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Llama a la función de tu ViewModel para eliminar la imagen
                        viewmodel.deleteImage(imageName, context)
                        navController.navigate("camera")
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