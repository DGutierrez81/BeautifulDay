package com.project.beautifulday.Components

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
import com.project.beautifulday.Components.DialogCategory
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.MyTopBar
import com.project.beautifulday.Components.ScreenCenter
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

/**
 * Composable que muestra una lista de locales. Permite al usuario visualizar los locales almacenados y realizar acciones como búsqueda, navegación y creación de nuevos locales.
 *
 * @param navController El controlador de navegación para la navegación entre pantallas.
 * @param viewmodel El ViewModel de comidas.
 * @param viewmodelA El ViewModel de la aplicación.
 * @param LgViewModel El ViewModel de inicio de sesión.
 * @param cocktailViewmodel El ViewModel de cócteles.
 */
@Composable
fun ListLocal(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel
){
    // Observa y obtiene los datos de la comida actual
    val localData by viewmodelA.localData.collectAsState()
    // Observa y obtiene la lista de comidas creadas por el usuario
    val meals by viewmodel.mealsData.collectAsState()
    // Determina si se debe mostrar el texto de búsqueda
    val showOutLineText = viewmodel.showOutLineText
    // Observa y obtiene el estado actual del deslizamiento
    val slide by viewmodelA.slide.observeAsState(false)
    // Observa y obtiene el estado actual de mostrar el diálogo
    val showDialog = viewmodelA.showDialog
    // Observa y obtiene el estado actual de inicio de sesión
    val login = LgViewModel.login
    // Observa y obtiene el estado actual del progreso
    val progrees by viewmodelA.progrees.observeAsState(true)

    val screen = viewmodelA.screen
    var order = 3
    var showCenter = 2
    if(screen == "meal") {
        showCenter = 2
        order = 3
    } else {
        showCenter = 3
        order = 5
    }

    // Realiza acciones al lanzar el efecto
    LaunchedEffect(Unit){
        viewmodelA.fetchLocal("Locales $screen")
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
                cocktailViewmodel= cocktailViewmodel,
                login = login,
                mealName = "Socios",
                navController = navController,
                slide = slide,
                viewmodelA = viewmodelA,
                showDialog = showDialog
            )
        },
        bottomBar = {
            // Muestra la barra de navegación inferior personalizada
            MyBottomBar(order = order, navController = navController, LgViewModel = LgViewModel, viewmodelA)
        }
    ) {innerPadding ->
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
                    showCenter = showCenter
                )
                // Muestra la lista de comidas creadas por el usuario
                Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                    viewmodelA.ShowLocal(localData = localData, navController, "Locales $screen")
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