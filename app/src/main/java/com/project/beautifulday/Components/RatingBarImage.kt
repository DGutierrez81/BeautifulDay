package com.project.beautifulday.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.project.beautifulday.R
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Composable que muestra una barra de calificación utilizando imágenes para representar las estrellas.
 *
 * @param modifier Modificador que controla el tamaño y el diseño de la barra de calificación.
 * @param rating Calificación actual.
 * @param stars Número total de estrellas en la barra de calificación.
 * @param starsColor Color de las estrellas.
 */
@Composable
fun RatingBarImage(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    // Calcula el número de estrellas llenas, medias y vacías
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    // Fila que contiene las estrellas de la barra de calificación
    Row(modifier = modifier) {
        // Itera sobre las estrellas llenas y muestra los íconos correspondientes
        repeat(filledStars) {
            Icon(imageVector = Icons.Outlined.Star, contentDescription = null, tint = starsColor)
        }
        // Muestra una estrella media si la calificación tiene parte decimal
        if (halfStar) {
            Icon(
                painter = painterResource(R.drawable.star_half),
                contentDescription = null,
                tint = starsColor
            )
        }
        // Itera sobre las estrellas vacías y muestra los íconos correspondientes
        repeat(unfilledStars) {
            Icon(
                painter = painterResource(R.drawable.star_borde),
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}
