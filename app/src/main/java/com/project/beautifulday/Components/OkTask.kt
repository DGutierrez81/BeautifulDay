package com.project.beautifulday.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R

/**
 * Composable que muestra un mensaje de confirmación cuando se ha completado una tarea de registro.
 *
 * @param viewmodel ViewModel asociado a la tarea de registro.
 * @param viewmodelA ViewModel de la aplicación.
 * @param cocktailViewmodel ViewModel asociado a las tareas de cócteles.
 */
@Composable
fun OkTask(
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    cocktailViewmodel: CocktailViewmodel
) {
    // Observa el progreso de la tarea de registro de comidas
    val progressMeal by viewmodel.progressCreated.observeAsState(true)
    // Observa el progreso de la tarea de registro de cócteles
    val progressCocktail by cocktailViewmodel.progressCreated.observeAsState(true)

    // Contenedor principal con alineación en el centro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.electricBlue)),
        contentAlignment = Alignment.Center
    ) {
        // Columna que alinea horizontalmente en el centro y tiene un relleno
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Verifica si se ha completado alguna de las tareas de registro
            if (!progressMeal || !progressCocktail) {
                // Muestra un icono de éxito y un mensaje de confirmación
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Icono de Éxito",
                        tint = Color.Green,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "Registro guardado correctamente",
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
            } else {
                // Muestra un indicador de progreso y un texto de guardado
                Column {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.paynesGray),
                        strokeWidth = 6.dp,
                        modifier = Modifier.scale(2.0f)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Guardando" + viewmodelA.getAnimatedDots(
                            progressMeal || progressCocktail
                        ),
                        color = colorResource(id = R.color.paynesGray),
                        modifier = Modifier.scale(2.0f)
                    )
                }
            }
        }
    }
}
