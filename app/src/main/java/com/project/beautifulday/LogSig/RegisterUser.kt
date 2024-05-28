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
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun RegisterUser(navController: NavController, loginVM: LogViewmodel, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, cocktailViewmodel: CocktailViewmodel){
    val meals by viewmodel.mealsData.collectAsState()
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val showAlert = loginVM.showAlert.value

    if(showAlert){
        Alert(onDismiss = { loginVM.showAlert(showAlert) }, titulo = "Aviso", contenido = "Error al ingresar los datos")
    }
    Scaffold(
        topBar = {
            MyTopBar(
            meals = meals,
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
        bottomBar = { MyBottomBar(order = 7, navController, loginVM, viewmodelA) }
    ) {innerPadding ->
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

@Composable
fun MyTopBar(
    meals: List<MealState>,
    showMenu: Boolean,
    viewmodel: MealViewmodel,
    showOutLineText: Boolean,
    cocktailViewmodel: CocktailViewmodel,
    login: Boolean,
    mealName: String,
    navController: NavController,
    slide: Boolean,
    viewmodelA: ViewmodelAplication,
    showDialog: Boolean
) {

}
