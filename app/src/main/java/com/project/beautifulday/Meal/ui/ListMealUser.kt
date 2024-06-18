package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.beautifulday.Components.BusquedaNombre
import com.project.beautifulday.Components.CreateDialog
import com.project.beautifulday.Components.DialogCategory
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.MyTopBar
import com.project.beautifulday.Components.ScreenCenter
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel


/**
 * Composable para mostrar una lista de comidas del usuario.
 *
 * Esta función recibe varios parámetros necesarios para mostrar y controlar las comidas del usuario.
 *
 * @param navController NavController para manejar la navegación en la aplicación.
 * @param viewmodel ViewModel relacionado con la información de las comidas.
 * @param context ComponentActivity para acceder al contexto de la aplicación.
 * @param viewmodelA ViewModel de la aplicación para manejar ciertas acciones y estados de la aplicación.
 * @param LgViewModel ViewModel para el inicio de sesión.
 * @param cocktailViewmodel ViewModel relacionado con información de cócteles.
 */
@Composable
fun ListMealUser(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel,
    context: ComponentActivity
){
    // Observa y obtiene los datos de la comida actual
    val mealData by viewmodel.mealData.collectAsState()
    // Observa y obtiene la lista de comidas del usuario
    val meals by viewmodel.mealsData.collectAsState()
    // Determina si se debe mostrar el texto de búsqueda
    val showOutLineText = viewmodel.showOutLineText
    // Observa y obtiene el estado actual del deslizamiento
    val slide by viewmodelA.slide.observeAsState(false)
    val slideUser by viewmodelA.slideUser.observeAsState(false)
    // Observa y obtiene el estado actual de mostrar el diálogo
    val showDialog = viewmodelA.showDialog
    // Observa y obtiene el estado actual de inicio de sesión
    val login = LgViewModel.login
    // Observa y obtiene el estado actual del progreso
    val progrees by viewmodel.progress.observeAsState(true)
    val iDoc = LgViewModel.user.idDocument

    val showAlert = viewmodelA.showAlert

    // Realiza acciones al lanzar el efecto
    LaunchedEffect(Unit){
        viewmodel.fetchMeal()
    }

    // Muestra el diálogo de categorías si se activa
    if(showDialog) {
        DialogCategory(
            onDismiss = { viewmodelA.chageShowDialog(showDialog) },
            lista = meals,
            viewmodel = viewmodel,
            viewmodelA,
            navController = navController
        )
    }

    // Diseño de la pantalla con Scaffold de Jetpack Compose
    Scaffold(
        topBar = {
            // Muestra la barra de navegación superior personalizada
            MyTopBar(
                showMenu = true,
                viewmodel = viewmodel,
                showOutLineText = showOutLineText,
                cocktailViewmodel =cocktailViewmodel,
                login = login,
                mealName = "Favoritos",
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
            // Muestra la barra de navegación inferior personalizada
            MyBottomBar(order = 3, navController = navController, LgViewModel = LgViewModel, viewmodelA, context)
        }
    ) { innerPadding ->
        CreateDialog(showAlert = showAlert, tittle = "Aviso", text = "¿Desea borrar el registro?", onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
            viewmodelA.deleteRegister(iDoc?:"", "Users",{navController.navigate("ok")}) {
                LgViewModel.deleteUser(context)
                LgViewModel.changeLogin(false)
                LgViewModel.logOut{
                    navController.navigate("principal") {
                        // Limpia la pila de navegación hasta el destino inicial
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        // Esto asegura que la pantalla principal sea la única en la pila de backstack
                        launchSingleTop = true
                    }
                }
            }
            viewmodelA.changeAlert(!showAlert)

        }

        // Contenido principal del Scaffold
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            // Muestra el indicador de progreso si la carga está en curso
            if(progrees) {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = "Cargando" + viewmodelA.getAnimatedDots(
                                progrees
                            )
                        )
                    }
                }
            } else {
                // Muestra el contenido principal cuando la carga ha finalizado
                ScreenCenter(
                    navController = navController,
                    viewmodelA = viewmodelA,
                    LgViewModel = LgViewModel,
                    showCenter = 2,
                    context = context
                )
                // Muestra la lista de comidas del usuario
                Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                    viewmodel.ShowMealsNameUser(mealData = mealData, navController, "Meals")
                }
                // Muestra el componente de búsqueda por nombre si está activado
                if(showOutLineText){
                    BusquedaNombre(
                        navController = navController,
                        viewmodel = viewmodel,
                        viewmodelA = viewmodelA,
                        showOutLineText = showOutLineText,
                        cocktailViewmodel = cocktailViewmodel
                    )
                }
            }
        }
    }
}
