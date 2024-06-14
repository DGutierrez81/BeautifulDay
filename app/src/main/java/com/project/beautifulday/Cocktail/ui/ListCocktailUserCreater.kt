package com.project.beautifulday.Cocktail.ui


import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication


/**
 * Pantalla para mostrar la lista de cócteles creados por el usuario.
 *
 * @param navController Controlador de navegación para manejar las transiciones entre pantallas.
 * @param viewmodel Instancia del [MealViewmodel] para realizar acciones relacionadas con las comidas.
 * @param viewmodelA Instancia del [ViewmodelAplication] para realizar acciones relacionadas con la aplicación.
 * @param LgViewModel Instancia del [LogViewmodel] para realizar acciones relacionadas con el inicio de sesión.
 * @param cocktailViewmodel Instancia del [CocktailViewmodel] para realizar acciones relacionadas con los cócteles.
 */
@Composable
fun ListCocktailUserCreater(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    cocktailViewmodel: CocktailViewmodel,
    context: ComponentActivity
) {
    // Observa los datos de cócteles creados por el usuario
    val cocktailData by cocktailViewmodel.cocktailUser.collectAsState()
    // Observa los datos de comidas del ViewModel
    val meals by viewmodel.mealsData.collectAsState()
    // Observa el estado de showOutLineText del ViewModel de comidas
    val showOutLineText = viewmodel.showOutLineText
    // Observa el estado del slide del ViewModel de aplicación
    val slide by viewmodelA.slide.observeAsState(false)
    // Observa el estado del showDialog del ViewModel de aplicación
    val showDialog = viewmodelA.showDialog
    // Observa el estado de progreso del ViewModel de cócteles
    val progrees by cocktailViewmodel.progress.observeAsState(true)
    // Observa el estado de login del ViewModel de inicio de sesión
    val login = LgViewModel.login
    val screen = viewmodelA.screen
    val iDoc = LgViewModel.user.idDocument

    val showAlert = viewmodelA.showAlert

    // Realiza la carga inicial de los cócteles creados por el usuario
    LaunchedEffect(key1 = true) {
        cocktailViewmodel.fetchCocktailCreater("Create $screen")
    }

    // Si showDialog es verdadero, muestra el diálogo de categoría
    if (showDialog) {
        DialogCategory(
            onDismiss = { viewmodelA.chageShowDialog(showDialog) },
            lista = meals,
            viewmodel = viewmodel,
            viewmodelA,
            navController = navController
        )
    }

    // Configura el Scaffold que contiene la estructura de la pantalla
    Scaffold(
        topBar = {
            // Configuración de la barra superior personalizada
            MyTopBar(
                showMenu = true,
                viewmodel = viewmodel,
                showOutLineText = showOutLineText,
                cocktailViewmodel = cocktailViewmodel,
                login = login,
                mealName = "Socios",
                navController = navController,
                slide = slide,
                viewmodelA = viewmodelA,
                logViewmodel = LgViewModel,
                showDialog = showDialog,
                context = context
            )
        },
        bottomBar = {
            // Configuración de la barra inferior personalizada
            MyBottomBar(order = 5, navController = navController, LgViewModel = LgViewModel, viewmodelA, context)
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            // Si está en progreso, muestra un indicador de carga
            if (progrees) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = colorResource(id = R.color.paynesGray))
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = "Cargando" + viewmodelA.getAnimatedDots(progrees),
                            color = colorResource(id = R.color.paynesGray)
                        )
                    }
                }
            } else {
                // Muestra el contenido central de la pantalla
                ScreenCenter(
                    navController = navController,
                    viewmodelA = viewmodelA,
                    LgViewModel = LgViewModel,
                    showCenter = 3,
                    context = context
                )
                Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)) {
                    // Muestra los nombres de los cócteles creados por el usuario
                    cocktailViewmodel.ShowCocktailNameUser(cocktailData = cocktailData, navController, "Create $screen")
                }
                // Si showOutLineText es verdadero, muestra el campo de búsqueda
                if (showOutLineText) {
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
