package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageCenter(model: Any?, s: String){
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter){
        AsyncImage(
            model = model, contentDescription = "olla",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(bottom = 10.dp),
            contentScale = ContentScale.Crop
        )
    }
}