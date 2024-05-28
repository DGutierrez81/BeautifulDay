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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.beautifulday.Components.BusquedaNombre
import com.project.beautifulday.Components.DialogCategory
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.ScreenCenter
import com.project.beautifulday.LogSig.MyTopBar
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

@Composable
fun ListaCocktailsApi(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel){
    val meals by viewmodel.mealsData.collectAsState()
    val cocktails by cocktailViewmodel.cocktailData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login
    var order = 2
    if(login) order = 5

    if(showDialog) DialogCategory(
        onDismiss = { viewmodelA.chageShowDialog(showDialog) },
        lista = meals,
        viewmodel = viewmodel,
        viewmodelA,
        navController = navController
    )


    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBar(
            meals,
            true,
            viewmodel,
            showOutLineText,
            cocktailViewmodel,
            login,
            "Api",
            navController,
            slide,
            viewmodelA,
            showDialog
        ) },
        bottomBar = { MyBottomBar(order, navController, LgViewModel, viewmodelA) }

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
            Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                cocktailViewmodel.ShowCocktailsName(cocktail = cocktails, navController = navController)
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
        /*
        innerPadding ->
        MyContent(
            innerPadding = innerPadding,
            viewmodel = viewmodel,
            viewmodelA = viewmodelA,
            LgViewModel = LgViewModel,
            navController = navController,
            showOutLinedText = showOutLineText,
            showListMeals = true,
            meals = meals,
            showViewCenter = 2
        )

         */
    }
}