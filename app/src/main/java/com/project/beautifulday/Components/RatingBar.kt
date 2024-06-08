package com.project.beautifulday.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.project.beautifulday.R

/**
 * Composable que muestra una barra de calificación.
 *
 * @param modifier Modificador que controla el tamaño y el diseño de la barra de calificación.
 * @param rating Calificación actual.
 * @param stars Número total de estrellas en la barra de calificación.
 * @param starsColor Color de las estrellas.
 * @param onRatingChanged Función de callback que se llama cuando cambia la calificación.
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    onRatingChanged: (Double) -> Unit
) {
    // Fila que contiene las estrellas de la barra de calificación
    Row(modifier = modifier) {
        // Itera sobre el número total de estrellas
        repeat(stars) { index ->
            // Determina el ícono a mostrar según la calificación actual
            val icon = if (index < rating) {
                painterResource(R.drawable.star) // Estrella llena si el índice es menor que la calificación
            } else {
                painterResource(R.drawable.star_borde) // Estrella vacía si el índice es mayor o igual a la calificación
            }
            // Icono representando una estrella con capacidad de cambiar la calificación al hacer clic
            Icon(
                painter = icon,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.clickable {
                    val newRating = index + 1.toDouble() // Ajusta la calificación al índice de la estrella
                    onRatingChanged(newRating)
                }
            )
        }
    }
}
