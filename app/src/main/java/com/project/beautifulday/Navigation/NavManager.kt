package com.project.beautifulday.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.beautifulday.Components.BlankView
import com.project.beautifulday.Cocktail.ui.CocktailScreen
import com.project.beautifulday.Cocktail.ui.ListCocktailUser
import com.project.beautifulday.Cocktail.ui.ListCocktailUserCreater
import com.project.beautifulday.Cocktail.ui.ListaCocktailsApi
import com.project.beautifulday.Cocktail.ui.CardCocktailUser
import com.project.beautifulday.Cocktail.ui.CardCocktails
import com.project.beautifulday.Components.RegistroM
import com.project.beautifulday.LogSig.LogIn
import com.project.beautifulday.Components.Camera
import com.project.beautifulday.Meal.ui.CardIngredient
import com.project.beautifulday.Meal.ui.CardMealUser
import com.project.beautifulday.Meal.ui.ListCategory
import com.project.beautifulday.Meal.ui.ListMealUser
import com.project.beautifulday.Meal.ui.ListMealUserCreater
import com.project.beautifulday.Meal.ui.MealNameScreen
import com.project.beautifulday.Meal.ui.MealScreen
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.Components.PrincipalScreen
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.LogSig.RegisterUser
import com.project.beautifulday.Components.CreateRegister
import com.project.beautifulday.Components.OkTask
import com.project.beautifulday.Meal.ui.CardMeals
import com.project.beautifulday.Components.Video
import com.project.beautifulday.ViewModels.CocktailViewmodel

@Composable
fun NavManager(viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "blankView"){
        composable("blankView"){
            BlankView(navController = navController, LgViewModel)
        }
        composable("principal"){
            PrincipalScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel,cocktailViewmodel)
        }
        composable("meal"){
            MealScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel, cocktailViewmodel)
        }
        composable("mealNameScreen"){
            MealNameScreen(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel, cocktailViewmodel)
        }
        composable("myCard"){
            CardMeals(navController = navController, viewmodel = viewmodel, context = context, viewmodelA, LgViewModel)
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
                viewmodelA,
                cocktailViewmodel
            )
        }
        composable("register"){
            RegisterUser(
                navController,
                LgViewModel,
                viewmodel,
                viewmodelA,
                cocktailViewmodel
            )
        }
        composable("listMealUser"){
            ListMealUser(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
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
            CreateRegister(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                context = context,
                cocktailViewmodel = cocktailViewmodel
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
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

        composable("video"){
            Video(viewmodelA = viewmodelA)
        }

        composable("cocktail"){
            CocktailScreen(navController, viewmodel, context, viewmodelA, LgViewModel, cocktailViewmodel)
        }
        //ListaCocktailsApi

        composable("listaCocktailsApi"){
            ListaCocktailsApi(navController, viewmodel, context, viewmodelA, LgViewModel, cocktailViewmodel)
        }

        composable("listCocktailUser"){
            ListCocktailUser(navController, viewmodel, context, viewmodelA, LgViewModel, cocktailViewmodel)
        }

        composable("cardCocktails"){
            CardCocktails(navController = navController, viewmodel = cocktailViewmodel, context = context, viewmodelA, LgViewModel)
        }

        composable("cardCocktailUser/{idDrink}?colec={colec}",
            arguments = listOf(
                navArgument("idDrink") { type = NavType.StringType },
                navArgument("colec") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val idDoc = it.arguments?.getString("idDrink") ?: ""
            val colec = it.arguments?.getString("colec") ?: ""


            CardCocktailUser(
                navController = navController,
                viewmodel = cocktailViewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                Idoc = idDoc,
                colec = colec
            )
        }

        composable("listCocktailUserCreater"){
            ListCocktailUserCreater(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

        composable("registroM"){
            RegistroM(
                navController = navController,
                viewmodel = viewmodelA,
                context = context
            )
        }
        composable("ok"){
            OkTask(
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

    }
}