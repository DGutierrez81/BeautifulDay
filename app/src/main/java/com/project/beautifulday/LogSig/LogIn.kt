package com.project.beautifulday.LogSig


import androidx.activity.ComponentActivity
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
 * Composable function for the login screen.
 *
 * @param navController NavController to handle navigation within the app.
 * @param loginVM ViewModel for handling login logic.
 * @param viewmodel ViewModel for managing meal data.
 * @param viewmodelA ViewModel for managing application-wide data.
 * @param cocktailViewmodel ViewModel for managing cocktail data.
 */
@Composable
fun LogIn(
    navController: NavController,
    loginVM: LogViewmodel,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    cocktailViewmodel: CocktailViewmodel,
    context: ComponentActivity
) {
    // Collecting state values from the ViewModels
    val meals by viewmodel.mealsData.collectAsState()
    val slide by viewmodelA.slide.observeAsState(false)
    val slideUser by viewmodelA.slideUser.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val showAlert = loginVM.showAlert.value

    // Show an alert dialog if showAlert is true
    if (showAlert) {
        Alert(
            titulo = "Aviso",
            contenido = "Error al ingresar los datos",
            onDismiss = { loginVM.showAlert(showAlert) }
        )
    }

    // Scaffold for the login screen layout
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
                slideUser = slideUser,
                viewmodelA = viewmodelA,
                logViewmodel = loginVM,
                showDialog = showDialog,
                context = context
            )
        },
        bottomBar = {
            // Customized bottom bar
            MyBottomBar(order = 6, navController, loginVM, viewmodelA, context)
        }
    ) { innerPadding ->
        // Main content of the login screen
        MyContent(
            innerPadding = innerPadding,
            navController = navController,
            viewmodel = viewmodel,
            viewmodelA = viewmodelA,
            LgViewModel = loginVM,
            showOutLinedText = false,
            showListMeals = false,
            meals = meals,
            showViewCenter = 4,
            context = context
        )
    }
}
