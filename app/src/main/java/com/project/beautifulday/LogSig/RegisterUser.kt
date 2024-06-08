package com.project.beautifulday.LogSig

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.project.beautifulday.Components.Alert
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.MyContent
import com.project.beautifulday.Components.MyTopBar
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable function for the user registration screen.
 *
 * @param navController NavController to handle navigation within the app.
 * @param loginVM ViewModel for handling login logic.
 * @param viewmodel ViewModel for managing meal data.
 * @param viewmodelA ViewModel for managing application-wide data.
 * @param cocktailViewmodel ViewModel for managing cocktail data.
 */
@Composable
fun RegisterUser(
    navController: NavController,
    loginVM: LogViewmodel,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    cocktailViewmodel: CocktailViewmodel
) {
    // Collecting state values from the ViewModels
    val meals by viewmodel.mealsData.collectAsState()
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val showAlert = loginVM.showAlert.value

    // Show an alert dialog if showAlert is true
    if (showAlert) {
        Alert(
            onDismiss = { loginVM.showAlert(showAlert) },
            titulo = "Aviso",
            contenido = "Error al ingresar los datos"
        )
    }

    // Scaffold for the user registration screen layout
    Scaffold(
        topBar = {
            // Customized top bar
            MyTopBar(
                showMenu = false,
                viewmodel = viewmodel,
                showOutLineText = false,
                cocktailViewmodel,
                login = false,
                mealName = "",
                navController = navController,
                slide = slide,
                viewmodelA = viewmodelA,
                showDialog = showDialog
            )
        },
        bottomBar = {
            // Customized bottom bar
            MyBottomBar(order = 7, navController, loginVM, viewmodelA)
        }
    ) { innerPadding ->
        // Main content of the user registration screen
        MyContent(
            innerPadding = innerPadding,
            navController = navController,
            viewmodel = viewmodel,
            viewmodelA = viewmodelA,
            LgViewModel = loginVM,
            showOutLinedText = false,
            showListMeals = false,
            meals = meals,
            showViewCenter = 5
        )
    }
}


