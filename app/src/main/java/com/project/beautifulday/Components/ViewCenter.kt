package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication


/**
 * Composable para mostrar el contenido central de la pantalla según la opción seleccionada.
 *
 * @param showCenter Entero que indica la opción seleccionada.
 * @param navController Controlador de navegación para navegar entre pantallas.
 * @param viewmodelA ViewModel de la aplicación.
 * @param LgViewModel ViewModel para el inicio de sesión.
 */
@Composable
fun ViewCenter(
    showCenter: Int,
    navController: NavController,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    context: ComponentActivity
) {
    // Seleccionar el contenido central según la opción
    when (showCenter) {
        1 -> {
            // Mostrar imágenes de olla y copa
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyImage(R.drawable.olla, navController, "olla", "meal", "Vamos a comer", viewmodelA)
                MyImage(R.drawable.glass, navController, "copa", "cocktail", "¿Una copita?", viewmodelA)
            }
        }
        2 -> {
            // Mostrar imagen de olla
            ImageCenter(R.drawable.olla2, "olla")
        }
        3 -> {
            // Mostrar imagen de copa
            ImageCenter(R.drawable.glass, "copa")
        }
        4 -> {
            // Mostrar pantalla de inicio de sesión
            Log(navController, LgViewModel)
        }
        5 -> {
            // Mostrar pantalla de registro
            Register(navController, LgViewModel, context)
        }
    }
}

