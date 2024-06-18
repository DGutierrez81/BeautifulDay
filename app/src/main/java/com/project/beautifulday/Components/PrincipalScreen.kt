package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel


/**
 * Pantalla principal de la aplicación.
 *
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param viewmodel Instancia del [MealViewmodel] para realizar acciones relacionadas con las comidas.
 * @param viewmodelA Instancia del [ViewmodelAplication] para realizar acciones relacionadas con la aplicación.
 * @param LgViewModel Instancia del [LogViewmodel] para realizar acciones relacionadas con el inicio de sesión.
 * @param cocktailViewmodel Instancia del [CocktailViewmodel] para realizar acciones relacionadas con los cócteles.
 */
@Composable
fun PrincipalScreen(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel,
    context: ComponentActivity
) {
    // Obtiene los datos de comidas del ViewModel
    val meals by viewmodel.mealsData.collectAsState()
    // Observa el estado del slide del ViewModel de aplicación
    val slide by viewmodelA.slide.observeAsState(false)
    val slideUser by viewmodelA.slideUser.observeAsState(false)
    // Observa el estado del showDialog del ViewModel de aplicación
    val showDialog = viewmodelA.showDialog
    // Observa el estado de login del ViewModel de inicio de sesión
    val login = LgViewModel.login
    // Determina el orden de los elementos en la pantalla
    var order = 1
    if (login) order = 8

    // Configura el Scaffold que contiene la estructura de la pantalla
    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = {
            // Configuración de la barra superior personalizada
            MyTopBar(
                showMenu = false,
                viewmodel = viewmodel,
                showOutLineText = false,
                cocktailViewmodel = cocktailViewmodel,
                login = false,
                mealName = "",
                navController = navController,
                slide = slide,
                slideUser = slideUser,
                viewmodelA = viewmodelA,
                logViewmodel = LgViewModel,
                showDialog = showDialog,
                context = context
            )
        },
        bottomBar = {
            // Configuración de la barra inferior personalizada
            MyBottomBar(order, navController, LgViewModel, viewmodelA, context)
        }
    ) { innerPadding ->
        // Contenido principal de la pantalla
        MyContent(
            innerPadding = innerPadding,
            navController = navController,
            viewmodel = viewmodel,
            viewmodelA = viewmodelA,
            LgViewModel = LgViewModel,
            showOutLinedText = false,
            showListMeals = false,
            meals = meals,
            showViewCenter = 1,
            context = context
        )
    }
}


