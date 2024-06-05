package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
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
import androidx.compose.material3.MaterialTheme
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
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R

@Composable
fun OkTask(viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel) {
    val progrees by viewmodel.progressCreated.observeAsState(true)
    val progreesC by cocktailViewmodel.progressCreated.observeAsState(true)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.electricBlue)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (!progrees || !progreesC) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Icono de Éxito",
                        tint = Color.Green,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(text="Regitro guardado correctamente", color = colorResource(id = R.color.paynesGray))
                }
            } else {
                Column {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.paynesGray),
                        strokeWidth = 6.dp,
                        modifier = Modifier.scale(2.5f)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Cargando" + viewmodelA.getAnimatedDots(
                            progrees || progreesC
                        ), color = colorResource(id = R.color.paynesGray),
                        modifier = Modifier.scale(2.5f)
                    )
                }
            }
        }
    }
}