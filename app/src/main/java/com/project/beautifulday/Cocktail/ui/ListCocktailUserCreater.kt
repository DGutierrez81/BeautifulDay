package com.project.beautifulday.Cocktail.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.project.beautifulday.Meal.ui.Components.BusquedaNombre
import com.project.beautifulday.Meal.ui.Components.DialogCategory
import com.project.beautifulday.Meal.ui.Components.MyBottomBar
import com.project.beautifulday.Meal.ui.Components.MyTopBar
import com.project.beautifulday.Meal.ui.Components.ScreenCenter
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun ListCocktailUserCreater(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel){
    val cocktailData by cocktailViewmodel.cocktailUser.collectAsState()
    val meals by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login

    LaunchedEffect(key1 = true){
        cocktailViewmodel.fetchCocktailCreater()
    }

    if(showDialog) DialogCategory(
        onDismiss = { viewmodelA.chageShowDialog(showDialog) },
        lista = meals,
        viewmodel = viewmodel,
        viewmodelA,
        navController = navController
    )

    Scaffold(
        topBar = {
            MyTopBar(
                meals = meals,
                showMenu = true,
                viewmodel = viewmodel,
                showOutLineText = showOutLineText,
                cocktailViewmodel,
                login = login,
                mealName = "Socios",
                navController = navController,
                slide = slide,
                viewmodelA = viewmodelA,
                showDialog = showDialog
            )
        },
        bottomBar = {
            MyBottomBar(order = 3, navController = navController, LgViewModel = LgViewModel)
        }
    ) {innerPadding ->
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
                showCenter = 2
            )
            Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                cocktailViewmodel.ShowCocktailNameUser(cocktailData = cocktailData, navController, "CreateCocktails")
            }
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