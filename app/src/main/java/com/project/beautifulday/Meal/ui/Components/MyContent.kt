package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.Meal.ui.ViewModels.LogViewmodel
import com.project.beautifulday.Meal.ui.ViewModels.MealViewmodel
import com.project.beautifulday.Meal.ui.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne



/*
@Composable
fun MyContent(innerPadding: PaddingValues,
              navController: NavController,
              viewmodel: MealViewmodel,
              viewmodelA: ViewmodelAplication,
              LgViewModel: LogViewmodel,
              showOutLinedText: Boolean,
              showListMeals: Boolean,
              meals: List<MealState>,
              mealData: List<MealUser>,
              showViewCenter: Int) {

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
                    ViewCenter(showCenter = showViewCenter, navController = navController, viewmodel, LgViewModel = LgViewModel)
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
                                viewmodelA.clean()
                                viewmodel.changeMealName("")
                            })
                }
            }
        }
    }
}

 */


@Composable
fun MyContent(innerPadding: PaddingValues,
              navController: NavController,
              viewmodel: MealViewmodel,
              viewmodelA: ViewmodelAplication,
              LgViewModel: LogViewmodel,
              showOutLinedText: Boolean,
              showListMeals: Boolean,
              meals: List<MealState>,
              showViewCenter: Int) {

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
                    ViewCenter(showCenter = showViewCenter, navController = navController, viewmodel, LgViewModel = LgViewModel)
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
                                viewmodelA.clean()
                                viewmodel.changeMealName("")
                            })
                }
            }
        }
    }
}

