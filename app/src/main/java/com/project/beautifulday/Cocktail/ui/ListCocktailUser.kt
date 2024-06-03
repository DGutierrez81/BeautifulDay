package com.project.beautifulday.Cocktail.ui

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
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.MyTopBar
import com.project.beautifulday.Components.ScreenCenter
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import kotlinx.coroutines.delay

@Composable
fun ListCocktailUser(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel ){
   // val mealData by viewmodel.mealData.collectAsState()
    val cocktail by cocktailViewmodel.cocktailUser.collectAsState()

    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login
    val progrees by cocktailViewmodel.progress.observeAsState(true)

    LaunchedEffect(key1 = true){
        cocktailViewmodel.fetchCocktail()
    }

    /*
    if(showDialog) DialogCategory(
        onDismiss = { viewmodelA.chageShowDialog(showDialog) },
        lista = meals,
        viewmodel = viewmodel,
        viewmodelA,
        navController = navController
    )

     */

    Scaffold(
        topBar = {
            MyTopBar(
                meals = mutableListOf(),
                showMenu = true,
                viewmodel = viewmodel,
                showOutLineText = showOutLineText,
                cocktailViewmodel,
                login = login,
                mealName = "Favoritos",
                navController = navController,
                slide = slide,
                viewmodelA = viewmodelA,
                showDialog = showDialog
            )
        },
        bottomBar = {
            MyBottomBar(order = 5, navController = navController, LgViewModel = LgViewModel, viewmodelA)
        }
    ) {innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.electricBlue))
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                if(progrees){
                    Box(
                        Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = colorResource(id = R.color.paynesGray))
                            Spacer(modifier = Modifier.padding(3.dp))
                            androidx.compose.material3.Text(
                                text = "Cargando" + viewmodelA.getAnimatedDots(
                                    progrees
                                ), color = colorResource(id = R.color.paynesGray)
                            )
                        }
                    }
                }else{
                ScreenCenter(
                    navController = navController,
                    viewmodelA = viewmodelA,
                    LgViewModel = LgViewModel,
                    showCenter = 3
                )
                Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                    cocktailViewmodel.ShowCocktailNameUser(cocktailData = cocktail, navController, "Cocktails")
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
}