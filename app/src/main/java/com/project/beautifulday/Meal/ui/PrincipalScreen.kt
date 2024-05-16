package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.R
import com.project.beautifulday.inicio2.jotiOne

@Composable
fun PrincipalScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication){
    viewmodel.changeViewCenter(true)
    val meals by viewmodel.mealsData.collectAsState()
    val slide = viewmodelA.slide

    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBar(meals,false, viewmodel, false, false, "", navController, slide, viewmodelA) },
        bottomBar = { MyBottomBar(1) }
    ) { innerPadding -> MyContent(innerPadding, navController, viewmodel, false, false, meals, true)}
}

@Composable
fun MyTopBar(
    meals: List<MealState>,
    showMenu: Boolean,
    viewmodel: MealViewmodel,
    showOutLineText: Boolean,
    login: Boolean,
    mealName: String,
    navController: NavController,
    slide: Boolean,
    viewmodelA: ViewmodelAplication
){
    if(showMenu){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
        ) {

            if(showMenu){
                LazyRow(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
                ){
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
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable {
                                viewmodel.getRandom()
                                navController.navigate("myCard")
                            }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Column {
                            Text(
                                text = "Categorias",
                                fontFamily = jotiOne,
                                color = colorResource(id = R.color.paynesGray),
                                modifier = Modifier.clickable { viewmodelA.changeSlide(slide) }
                            )
                            AnimatedVisibility(
                                visible = slide
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .background(colorResource(id = R.color.electricBlue))
                                ) {
                                    Text(
                                        text = "Categoria", modifier = Modifier
                                            .padding(2.dp)
                                            .clickable {
                                                       viewmodel.getListCategory()
                                                navController.navigate("listCategory")
                                            },
                                        color = colorResource(id = R.color.paynesGray),
                                        fontFamily = jotiOne
                                    )
                                    Text(
                                        text = "Paises", modifier = Modifier
                                            .padding(2.dp)
                                            .clickable {
                                                viewmodel.getListArea()
                                                navController.navigate("listCategory")
                                                       },
                                        color = colorResource(id = R.color.paynesGray),
                                        fontFamily = jotiOne
                                    )
                                    Text(
                                        text = "Ingredientes", modifier = Modifier
                                            .padding(2.dp)
                                            .clickable {
                                                viewmodel.getListIngredient()
                                                navController.navigate("listCategory")
                                            },
                                        color = colorResource(id = R.color.paynesGray),
                                        fontFamily = jotiOne
                                    )
                                }
                            }
                        }
                        if(login){
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
                            Spacer(modifier = Modifier.padding(20.dp))
                            Text(
                                text = "Donde comer",
                                fontFamily = jotiOne,
                                color = colorResource(id = R.color.paynesGray)
                            )
                        }
                    }
                }
            }

            Column {
                Icon(
                    painter = painterResource(id = R.drawable.inicio2_sol),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 50.dp),
                    tint = Color.Yellow
                )
                Text(text = mealName, modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = jotiOne,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.paynesGray)
                    )
            }
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
        ) {

            Icon(
                painter = painterResource(id = R.drawable.inicio2_sol),
                contentDescription = null,
                modifier = Modifier.padding(start = 50.dp, top = 50.dp),
                tint = Color.Yellow
            )
        }
    }
}

@Composable
fun MyBottomBar(order: Int){
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
                horizontalArrangement = Arrangement.SpaceEvenly) {
                GroupIcon(order)
            }
        }
    }
}

@Composable
fun MyContent(innerPadding: PaddingValues,
              navController: NavController,
              viewmodel: MealViewmodel,
              showOutLinedText: Boolean,
              showListMeals: Boolean,
              meals: List<MealState>,
              showViewCenter: Boolean) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue))
            .padding(innerPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .fillMaxWidth(),
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
                    ViewCenter(showCenter = showViewCenter, navController = navController, viewmodel)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(id = R.color.selectiveYellow)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
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
        if(showListMeals){
            Box(modifier = Modifier.padding(start = 30.dp, end = 30.dp)){
                viewmodel.ShowMealsName(meals = meals, navController)
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
fun ViewCenter(showCenter: Boolean,
               navController: NavController,
               viewmodel: MealViewmodel){
    if(showCenter){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.olla, contentDescription = "olla",
                    modifier = Modifier
                        .height(50.dp)
                        .clickable { navController.navigate("meal") }
                )
                Text(
                    text = "Vamos a comer",
                    fontSize = 12.sp,
                    fontFamily = jotiOne,
                    color = colorResource(id = R.color.paynesGray),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.glass, contentDescription = "cocktail",
                    modifier = Modifier.height(50.dp)
                )
                Text(
                    text = "¿Una copita?",
                    fontSize = 12.sp,
                    fontFamily = jotiOne,
                    color = colorResource(id = R.color.paynesGray),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }else{
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
    }
}

@Composable
fun GroupIcon(order: Int){
    when(order){
        1 -> {
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
        }
        2 -> {
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
        3 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver)
            )
            AsyncImage(model = R.drawable.glass,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        4 -> {
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
            AsyncImage(model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
        5 -> {
            Icon(
                painter = painterResource(id = R.drawable.inicio2_vector),
                contentDescription = null,
                tint = colorResource(id = R.color.silver)
            )
            AsyncImage(model = R.drawable.olla,
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp))
        }
    }
}
