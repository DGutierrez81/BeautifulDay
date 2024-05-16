package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun ListCategory(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication){
    val mealsData by viewmodel.mealsData.collectAsState()
    val ingredientData by viewmodel.ingredientData.collectAsState()
    
    LazyColumn(){
        items(ingredientData){item ->
            Column {
                Text(text = item.idIngredient?:"")
                Text(text = item.strIngredient?:"")
                Text(text = item.strType?:"")
                Text(text = item.strDescription?:"")
            }
            /*
            if(item.strCategory != null){
                Column {
                    Text(text = "Categorias:")
                    Text(text = item.strCategory?: "")
                }
            }
            if(item.strArea != null){
                Column {
                    Text(text = "Paises:")
                    Text(text = item.strArea?: "")
                }
            }
            
             */
        }
    }
}