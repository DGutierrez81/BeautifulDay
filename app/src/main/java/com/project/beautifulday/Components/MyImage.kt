package com.project.beautifulday.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne


/**
 * Composable que muestra una imagen con texto descriptivo y permite la navegación a una ruta específica.
 *
 * @param model Modelo de la imagen.
 * @param navController Controlador de navegación.
 * @param contentDescription Descripción del contenido de la imagen para accesibilidad.
 * @param route Ruta a la que se navegará al hacer clic en la imagen.
 * @param textDescription Descripción textual del contenido de la imagen.
 * @param viewmodelA ViewModel de la aplicación.
 */
@Composable
fun MyImage(
    model: Any?,
    navController: NavController,
    contentDescription: String,
    route: String,
    textDescription: String,
    viewmodelA: ViewmodelAplication
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Muestra la imagen de forma asíncrona
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = Modifier
                .height(50.dp)
                .clickable {
                    // Navega a la ruta específica al hacer clic en la imagen
                    viewmodelA.changeScreen(route)
                    navController.navigate(route)
                }
        )
        // Muestra el texto descriptivo debajo de la imagen
        Text(
            text = textDescription,
            fontSize = 12.sp,
            fontFamily = jotiOne,
            color = colorResource(id = R.color.paynesGray),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
