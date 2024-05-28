package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import com.project.beautifulday.Components.BusquedaNombre
import com.project.beautifulday.Components.DialogCategory
import com.project.beautifulday.Components.MyBottomBar
import com.project.beautifulday.Components.MyTopBar
import com.project.beautifulday.Components.ScreenCenter
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.androidsmall1.jotiOne


@Composable
fun MealScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, cocktailViewmodel: CocktailViewmodel){

    val meal by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showDialog = viewmodelA.showDialog
    val login = LgViewModel.login
    var order = 2
    if(login) order = 3

    if(showDialog) DialogCategory(onDismiss = { viewmodelA.chageShowDialog(showDialog) }, lista = meal, viewmodel, viewmodelA, navController)

    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBar(meal, true, viewmodel, showOutLineText, cocktailViewmodel, login, "Comida", navController, slide, viewmodelA, showDialog) },
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
                showCenter = 2
            )

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

@Composable
fun MyTopBarMeal(showOutLineText: Boolean, viewmodel: MealViewmodel, showMenu: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue))
    ) {

        if (showMenu) {
            LazyRow(
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                item {
                    Text(text = "Busqueda por nombre",
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray),
                        modifier = Modifier.clickable {
                            viewmodel.changeshowOutLineText(
                                showOutLineText
                            )
                        })
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Random",
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Categorias",
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Ver recetas socios",
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Crear receta",
                        fontFamily = jotiOne,
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.inicio2_sol),
            contentDescription = null,
            modifier = Modifier.padding(start = 50.dp),
            tint = Color.Yellow
        )
    }
}
}


/*
@Composable
fun MyContentMeal(
    innerPadding: PaddingValues,
    viewmodel: MealViewmodel,
    navController: NavController,
    showOutLinedText: Boolean
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
                    Box(modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter){
                        AsyncImage(
                            model = R.drawable.olla2, contentDescription = "olla",
                            modifier = Modifier
                                .height(200.dp)
                                .width(200.dp)
                                .padding(bottom = 10.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
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

@Composable
fun MyBottomBarMeal(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.selectiveYellow))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.inicio2_key),
                    contentDescription = null,
                    tint = colorResource(id = R.color.silver)
                )
                Icon(
                    painter = painterResource(id = R.drawable.inicio2_plus),
                    contentDescription = null,
                    tint = colorResource(id = R.color.silver)
                )
                AsyncImage(model = R.drawable.glass,
                    contentDescription = null,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp))
            }
        }
    }
}
 */