package com.project.beautifulday.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.beautifulday.BlankView
import com.project.beautifulday.LogIn
import com.project.beautifulday.Meal.ui.Camera
import com.project.beautifulday.Meal.ui.CardIngredient
import com.project.beautifulday.Meal.ui.CardMealUser
import com.project.beautifulday.Meal.ui.CreateNewMeal
import com.project.beautifulday.Meal.ui.ListCategory
import com.project.beautifulday.Meal.ui.ListMealUser
import com.project.beautifulday.Meal.ui.ListMealUserCreater
import com.project.beautifulday.Meal.ui.MealNameScreen
import com.project.beautifulday.Meal.ui.MealScreen
import com.project.beautifulday.Meal.ui.ViewModels.MealViewmodel
import com.project.beautifulday.Meal.ui.MyCard
import com.project.beautifulday.Meal.ui.PrincipalScreen
import com.project.beautifulday.Meal.ui.ViewModels.LogViewmodel
import com.project.beautifulday.Meal.ui.ViewModels.ViewmodelAplication
import com.project.beautifulday.RegisterUser
import com.project.beautifulday.Video

@Composable
fun NavManager(viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "blankView"){
        composable("blankView"){
            BlankView(navController = navController, LgViewModel)
        }
        composable("principal"){
            PrincipalScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel)
        }
        composable("meal"){
            MealScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel)
        }
        composable("mealNameScreen"){
            MealNameScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel)
        }
        composable("myCard"){
            MyCard(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel)
        }
        composable("listCategory"){
            ListCategory(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA
            )
        }
        composable("cardIngredient"){
            CardIngredient(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA
            )
        }

        composable("login"){
            LogIn(
                navController,
                LgViewModel,
                viewmodel,
                viewmodelA
            )
        }
        composable("register"){
            RegisterUser(
                navController,
                LgViewModel,
                viewmodel,
                viewmodelA
            )
        }
        composable("listMealUser"){
            ListMealUser(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel
            )
        }

        composable("cardMealUser/{idDrink}?colec={colec}",
            arguments = listOf(
                navArgument("idDrink") { type = NavType.StringType },
                navArgument("colec") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val idDoc = it.arguments?.getString("idDrink") ?: ""
            val colec = it.arguments?.getString("colec") ?: ""


            CardMealUser(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                Idoc = idDoc,
                colec = colec
            )
        }

        composable("createNewMeal"){
            CreateNewMeal(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                context = context
            )
        }
        
        composable("camera"){
            Camera(navController = navController, viewmodelA = viewmodelA, context = context)
        }

        composable("listMealUserCreater"){
            ListMealUserCreater(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel
            )
        }

        composable("video"){
            Video(navController = navController, viewmodelA = viewmodelA)
        }

    }
}