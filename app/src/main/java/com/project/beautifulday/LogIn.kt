package com.project.beautifulday

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.project.beautifulday.Meal.ui.Components.Alert
import com.project.beautifulday.Meal.ui.Components.MyBottomBar
import com.project.beautifulday.Meal.ui.Components.MyContent
import com.project.beautifulday.Meal.ui.Components.MyTopBar
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun LogIn(navController: NavController, loginVM: LogViewmodel, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, cocktailViewmodel: CocktailViewmodel){
    val meals by viewmodel.mealsData.collectAsState()
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val showAlert = loginVM.showAlert.value
    
    if(showAlert){
        Alert(titulo = "Aviso", contenido = "Error al ingresar los datos", onDismiss = { loginVM.showAlert(showAlert) })
    }
    Scaffold(
        topBar = {MyTopBar(
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
        )},
        bottomBar = { MyBottomBar(order = 6, navController, loginVM) }
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
            showViewCenter = 4
        )
    }
    
    
    
}