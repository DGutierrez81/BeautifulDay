package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.ViewmodelAplication


@Composable
fun ViewCenter(showCenter: Int,
               navController: NavController,
               viewmodelA: ViewmodelAplication,
               LgViewModel: LogViewmodel
){
    when(showCenter){
        1 -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MyImage(R.drawable.olla ,navController, "cooking pot", "meal", "Vamos a comer", viewmodelA)
                MyImage(R.drawable.glass, navController, "glass", "cocktail", "¿Una copita?", viewmodelA)
            }
        }
        2 -> {
            ImageCenter(R.drawable.olla2, "cooking pot")
        }
        3 -> {
            ImageCenter(model = R.drawable.glass, s = "glass")
            }
        4 -> {
            Log(navController, LgViewModel)
        }
        5 -> {
            Register(navController, LgViewModel)
        }
        }
    }

