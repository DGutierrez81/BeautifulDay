package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.project.beautifulday.R


@Composable
fun MealNameScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication){
    val meals by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText
    val slide = viewmodelA.slide



    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBar(
            meals,
            true,
            viewmodel,
            showOutLineText,
            false,
            "",
            navController,
            slide,
            viewmodelA
        ) },
        bottomBar = { MyBottomBar(2) }

    ) {
        innerPadding ->
        MyContent(
            innerPadding = innerPadding,
            viewmodel = viewmodel,
            navController = navController,
            showOutLinedText = showOutLineText,
            showListMeals = true,
            meals = meals,
            showViewCenter = false
        )
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