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
import com.project.beautifulday.Components.CreateLocal
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
import com.project.beautifulday.Components.MyGoogleMaps
import com.project.beautifulday.Components.OkTask
import com.project.beautifulday.Meal.ui.CardMeals
import com.project.beautifulday.Components.Video
import com.project.beautifulday.Components.CardLocalM
import com.project.beautifulday.Components.ListLocal
import com.project.beautifulday.ViewModels.CocktailViewmodel

/**
 * Administra la navegación entre diferentes pantallas de la aplicación.
 *
 * @param viewmodel Instancia del ViewModel para las comidas.
 * @param context Actividad actual.
 * @param viewmodelA Instancia del ViewModel de la aplicación.
 * @param LgViewModel Instancia del ViewModel para el inicio de sesión.
 * @param cocktailViewmodel Instancia del ViewModel para los cócteles.
 */
@Composable
fun NavManager(
    viewmodel: MealViewmodel,
    context: ComponentActivity,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel
) {
    // Recuerda el NavController para manejar la navegación
    val navController = rememberNavController()

    // Definición de las rutas de navegación
    NavHost(navController = navController, startDestination = "blankView") {
        // Pantalla en blanco como destino inicial
        composable("blankView") {
            BlankView(navController = navController, LgViewModel)
        }
        // Pantalla principal
        composable("principal") {
            PrincipalScreen(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA,
                LgViewModel,
                cocktailViewmodel
            )
        }
        // Pantalla de comidas
        composable("meal") {
            MealScreen(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA,
                LgViewModel,
                cocktailViewmodel
            )
        }
        // Pantalla de nombre de comida
        composable("mealNameScreen") {
            MealNameScreen(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA,
                LgViewModel,
                cocktailViewmodel
            )
        }
        // Pantalla de tarjetas de comidas
        composable("myCard") {
            CardMeals(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA,
                LgViewModel
            )
        }
        // Pantalla de lista de categorías
        composable("listCategory") {
            ListCategory(
                navController = navController,
                viewmodel = viewmodel,
                context = context,
                viewmodelA = viewmodelA
            )
        }
        // Pantalla de inicio de sesión
        composable("login") {
            LogIn(
                navController,
                LgViewModel,
                viewmodel,
                viewmodelA,
                cocktailViewmodel
            )
        }

        // Pantalla de registro de usuario.
        composable("register") {
            RegisterUser(
                navController,
                LgViewModel,
                viewmodel,
                viewmodelA,
                cocktailViewmodel
            )
        }

// Pantalla de lista de comidas del usuario.
        composable("listMealUser") {
            ListMealUser(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de tarjeta de comida del usuario.
        composable(
            route = "cardMealUser/{idDrink}?colec={colec}",
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
                Idoc = idDoc,
                colec = colec
            )
        }

// Pantalla de creación de nueva comida.
        composable("createNewMeal") {
            CreateRegister(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                context = context,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de cámara.
        composable(
            route = "camera/{email}?numb={numb}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("numb") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val email = it.arguments?.getString("email") ?: ""
            val numb = it.arguments?.getString("numb") ?: ""
            Camera(
                navController = navController,
                viewmodelA = viewmodelA,
                context = context,
                email = email,
                numb = numb
            )
        }

// Pantalla de lista de comidas creadas por el usuario.
        composable("listMealUserCreater") {
            ListMealUserCreater(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de vídeo.
        composable("video") {
            Video(viewmodelA = viewmodelA)
        }

// Pantalla de cocktails.
        composable("cocktail") {
            CocktailScreen(navController, viewmodel, viewmodelA, LgViewModel, cocktailViewmodel)
        }

// Pantalla de lista de cocktails desde la API.
        composable("listaCocktailsApi") {
            ListaCocktailsApi(navController, viewmodel, viewmodelA, LgViewModel, cocktailViewmodel)
        }

// Pantalla de lista de cocktails del usuario.
        composable("listCocktailUser") {
            ListCocktailUser(navController, viewmodel, viewmodelA, LgViewModel, cocktailViewmodel)
        }

        // Pantalla de tarjetas de cocktails.
        composable("cardCocktails") {
            CardCocktails(
                navController = navController,
                viewmodel = cocktailViewmodel,
                context = context,
                viewmodelA,
                LgViewModel
            )
        }

// Pantalla de tarjetas de cocktails del usuario.
        composable(
            route = "cardCocktailUser/{idDrink}?colec={colec}",
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
                Idoc = idDoc,
                colec = colec
            )
        }

// Pantalla de lista de cocktails creados por el usuario.
        composable("listCocktailUserCreater") {
            ListCocktailUserCreater(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de registro de usuario.
        composable(
            route = "registroM/{email}?numb={numb}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("numb") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val email = it.arguments?.getString("email") ?: ""
            val numb = it.arguments?.getString("numb") ?: ""
            RegistroM(
                navController = navController,
                viewmodel = viewmodelA,
                context = context,
                email = email,
                numb = numb
            )
        }


        // Pantalla de tarea completada.
        composable("ok") {
            OkTask(
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de creación de un nuevo local.
        composable("createNewLocal") {
            CreateLocal(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                context = context,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de lista de locales.
        composable("listLocal") {
            ListLocal(
                navController = navController,
                viewmodel = viewmodel,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                cocktailViewmodel = cocktailViewmodel
            )
        }

// Pantalla de mapas de Google personalizados.
        composable("myGoogleMaps") {
            MyGoogleMaps(viewModel = viewmodelA, context = context)
        }

// Pantalla de tarjeta de local del usuario.
        composable(
            route = "cardLocalM/{idDrink}?colec={colec}",
            arguments = listOf(
                navArgument("idDrink") { type = NavType.StringType },
                navArgument("colec") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val idDoc = it.arguments?.getString("idDrink") ?: ""
            val colec = it.arguments?.getString("colec") ?: ""
            CardLocalM(
                navController = navController,
                context = context,
                viewmodelA = viewmodelA,
                Idoc = idDoc,
                colec = colec
            )
        }
    }
}