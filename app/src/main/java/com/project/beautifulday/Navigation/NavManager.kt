package com.project.beautifulday.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.beautifulday.Meal.ui.MealNameScreen
import com.project.beautifulday.Meal.ui.MealScreen
import com.project.beautifulday.Meal.ui.MealViewmodel
import com.project.beautifulday.Meal.ui.PrincipalScreen

@Composable
fun NavManager(viewmodel: MealViewmodel, context: ComponentActivity){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "principal"){
        composable("principal"){
            PrincipalScreen(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("meal"){
            MealScreen(navController = navController, viewmodel = viewmodel, context = context)
        }
        composable("mealNameScreen"){
            MealNameScreen(navController = navController, viewmodel = viewmodel, context = context)
        }

    }
}