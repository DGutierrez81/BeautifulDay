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

@Composable
fun RatingBar(
    modifier: Modifier = Modifier.fillMaxWidth(),
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
    onRatingChanged: (Double) -> Unit
) {
    Row(modifier = modifier) {
        repeat(stars) { index ->
            val icon = if (index < rating) {
                painterResource(R.drawable.star)
            } else {
                painterResource(R.drawable.star_borde)
            }
            Icon(
                painter = icon,
                contentDescription = null,
                tint = starsColor,
                modifier = Modifier.clickable {
                    val newRating = index + 1.toDouble() // índice comienza desde 0
                    onRatingChanged(newRating)
                }
            )
        }
    }
}