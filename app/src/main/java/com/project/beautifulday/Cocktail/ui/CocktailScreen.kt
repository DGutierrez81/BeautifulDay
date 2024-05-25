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
import com.project.beautifulday.Meal.ui.Components.BusquedaNombre
import com.project.beautifulday.Meal.ui.Components.DialogCategory
import com.project.beautifulday.Meal.ui.Components.MyBottomBar
import com.project.beautifulday.Meal.ui.Components.MyTopBar
import com.project.beautifulday.Meal.ui.Components.ScreenCenter
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun CocktailScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel) {

    val meal by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login
    var order = 4
    if (login) order = 5

    if (showDialog) DialogCategory(
        onDismiss = { viewmodelA.chageShowDialog(showDialog) },
        lista = meal,
        viewmodel,
        viewmodelA,
        navController
    )

    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = {
            MyTopBar(
                meal,
                true,
                viewmodel,
                showOutLineText,
                login,
                "Cocktail",
                navController,
                slide,
                viewmodelA,
                showDialog
            )
        },
        bottomBar = { MyBottomBar(order, navController, LgViewModel) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            ScreenCenter(
                navController = navController,
                viewmodelA = viewmodelA,
                LgViewModel = LgViewModel,
                showCenter = 3
            )

            if (showOutLineText) {
                BusquedaNombre(
                    navController = navController,
                    viewmodel = viewmodel,
                    viewmodelA = viewmodelA,
                    showOutLineText = showOutLineText
                )
            }
        }
    }
}