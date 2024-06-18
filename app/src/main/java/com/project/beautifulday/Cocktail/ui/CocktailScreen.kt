package com.project.beautifulday.Cocktail.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.project.beautifulday.Components.BusquedaNombre
import com.project.beautifulday.Components.CreateDialog
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
 * Pantalla principal para la visualización de cocktails.
 *
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param viewmodel Instancia del [MealViewmodel] para realizar acciones relacionadas con las comidas.
 * @param viewmodelA Instancia del [ViewmodelAplication] para realizar acciones relacionadas con la aplicación.
 * @param LgViewModel Instancia del [LogViewmodel] para realizar acciones relacionadas con el inicio de sesión.
 * @param cocktailViewmodel Instancia del [CocktailViewmodel] para realizar acciones relacionadas con los cócteles.
 */
@Composable
fun CocktailScreen(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel,
    context: ComponentActivity
) {
    // Observa los datos de comidas del ViewModel
    val meal by viewmodel.mealsData.collectAsState()
    // Observa el estado de showOutLineText del ViewModel
    val showOutLineText = viewmodel.showOutLineText
    // Observa el estado del slide del ViewmodelAplication
    val slide by viewmodelA.slide.observeAsState(false)
    val slideUser by viewmodelA.slideUser.observeAsState(false)
    // Observa el estado del showDialog del ViewmodelAplication
    val showDialog = viewmodelA.showDialog
    // Observa el estado de login del LogViewmodel
    val login = LgViewModel.login

    val iDoc = LgViewModel.user.idDocument

    val showAlert = viewmodelA.showAlert

    // Variable para determinar el orden de los elementos en la pantalla
    var order = 4
    if (login) order = 5

    // Si showDialog es verdadero, muestra el diálogo de categoría
    if (showDialog) DialogCategory(
        onDismiss = { viewmodelA.chageShowDialog(showDialog) },
        lista = meal,
        viewmodel,
        viewmodelA,
        navController
    )

    // Configura el Scaffold que contiene la estructura de la pantalla
    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = {
            // Configuración de la barra superior personalizada
            MyTopBar(
                showMenu = true, // Indica que es la barra superior
                viewmodel = viewmodel, // ViewModel de comidas
                showOutLineText = showOutLineText, // Estado de showOutLineText
                cocktailViewmodel = cocktailViewmodel,
                login = login, // Estado de login
                mealName = "Cocktail", // Título de la pantalla
                navController = navController, // Controlador de navegación
                slide = slide, // Estado de slide
                slideUser = slideUser,
                viewmodelA = viewmodelA, // ViewModel de aplicación
                LgViewModel,
                showDialog = showDialog, // Estado de showDialog
                context = context
            )
        },
        bottomBar = {
            // Configuración de la barra inferior personalizada
            MyBottomBar(order, navController, LgViewModel, viewmodelA, context)
        }
    ) { innerPadding ->
        CreateDialog(showAlert = showAlert, tittle = "Aviso", text = "¿Desea borrar el registro?", onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
            viewmodelA.deleteRegister(iDoc?:"", "Users", {navController.navigate("ok")}) {
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

        // Caja que contiene el contenido principal de la pantalla
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho disponible
                .background(colorResource(id = R.color.electricBlue)) // Fondo azul eléctrico
                .padding(innerPadding), // Padding interno
            contentAlignment = Alignment.TopCenter // Alineación del contenido al centro superior
        ) {
            // Contenido central de la pantalla
            ScreenCenter(
                navController = navController, // Controlador de navegación
                viewmodelA = viewmodelA, // ViewModel de aplicación
                LgViewModel = LgViewModel, // ViewModel de login
                showCenter = 3, // Indica el contenido a mostrar en el centro
                context = context
            )

            // Si showOutLineText es verdadero, muestra el campo de búsqueda
            if (showOutLineText) {
                BusquedaNombre(
                    navController = navController, // Controlador de navegación
                    viewmodel = viewmodel, // ViewModel de comidas
                    viewmodelA = viewmodelA, // ViewModel de aplicación
                    showOutLineText = showOutLineText, // Estado de showOutLineText
                    cocktailViewmodel = cocktailViewmodel // ViewModel de cocktails
                )
            }
        }
    }
}

