package com.project.beautifulday.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.beautifulday.Meal.ui.ListCategory
import com.project.beautifulday.Meal.ui.MealNameScreen
import com.project.beautifulday.Meal.ui.MealScreen
import com.project.beautifulday.Meal.ui.MealViewmodel
import com.project.beautifulday.Meal.ui.MyCard
import com.project.beautifulday.Meal.ui.PrincipalScreen
import com.project.beautifulday.Meal.ui.ViewmodelAplication

@Composable
fun NavManager(viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "principal"){
        composable("principal"){
            PrincipalScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA)
        }
        composable("meal"){
            MealScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA)
        }
        composable("mealNameScreen"){
            MealNameScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA)
        }
        composable("myCard"){
            MyCard(navController = navController, viewmodel = viewmodel, context = context, viewmodelA)
        }
        composable("listCategory"){
            ListCategory(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA
            )
        }

    }
}