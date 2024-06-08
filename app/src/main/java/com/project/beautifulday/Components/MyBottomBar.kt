package com.project.beautifulday.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable que representa la barra inferior de la aplicación.
 *
 * @param order Orden de visualización de los iconos.
 * @param navController Controlador de navegación de Jetpack Compose.
 * @param LgViewModel ViewModel para el manejo de inicio de sesión.
 * @param viewmodelA ViewModel de la aplicación.
 */
@Composable
fun MyBottomBar(order: Int, navController: NavController, LgViewModel: LogViewmodel, viewmodelA: ViewmodelAplication){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.selectiveYellow))
    ) {
        // Contenedor principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            // Fila para los iconos de la barra inferior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                // Llamada a la función para mostrar los iconos según el orden
                GroupIcon(order, navController, LgViewModel = LgViewModel, viewmodelA)
            }
        }
    }
}
