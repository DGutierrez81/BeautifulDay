package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.Components.BusquedaNombre
import com.project.beautifulday.Meal.ui.Components.DialogCategory
import com.project.beautifulday.Meal.ui.Components.MyBottomBar
import com.project.beautifulday.Meal.ui.Components.MyTopBar
import com.project.beautifulday.Meal.ui.Components.ScreenCenter
import com.project.beautifulday.Meal.ui.Components.ViewCenter
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


@Composable
fun MealNameScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel){
    val meals by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login
    var order = 2
    if(login) order = 3

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
            login,
            "Api",
            navController,
            slide,
            viewmodelA,
            showDialog
        ) },
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
                showCenter = 2
            )
            Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                viewmodel.ShowMealsName(meals = meals, navController)
            }
            if(showOutLineText){
                BusquedaNombre(
                    navController = navController,
                    viewmodel = viewmodel,
                    viewmodelA = viewmodelA,
                    showOutLineText = showOutLineText
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


/*
@Composable
fun MyContentMeal2(
    innerPadding: PaddingValues,
    viewmodel: MealViewmodel,
    navController: NavController,
    showOutLinedText: Boolean,
    meals: List<MealState>
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue))
            .padding(innerPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue)),
                contentAlignment = Alignment.TopEnd
            ){
                Icon(painter = painterResource(id = R.drawable.inicio2_nube),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 50.dp, end = 50.dp),
                    tint = colorResource(id = R.color.white)
                )
            }
            Text(
                text = "Beautiful",
                fontSize = 32.sp,
                fontFamily = jotiOne,
                color = colorResource(id = R.color.selectiveYellow),
                modifier = Modifier.padding(top = 50.dp)
            )
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter){
                Column {
                    ViewCenter(false, navController, viewmodel)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.selectiveYellow)),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        androidx.compose.material.Text(
                            text = "DAY",
                            fontFamily = jotiOne,
                            fontSize = 32.sp,
                            color = colorResource(id = R.color.electricBlue),
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
            viewmodel.ShowMealsName(meals = meals)
        }

        if(showOutLinedText){
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center){
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(value = viewmodel.mealName, onValueChange = { viewmodel.changeMealName(it)},
                        label = { Text(text = "Busqueda por nombre") },
                        modifier = Modifier
                            .padding(5.dp)
                            .background(color = Color.Transparent),
                        shape = RoundedCornerShape(100.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White
                        )
                    )
                    AsyncImage(model = R.drawable.logo,
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clickable {
                                viewmodel.getMealName(viewmodel.mealName)
                                navController.navigate("mealNameScreen")
                                viewmodel.changeshowOutLineText(showOutLinedText)
                                viewmodel.changeMealName("")
                            })
                }
            }
        }
    }
}


 */