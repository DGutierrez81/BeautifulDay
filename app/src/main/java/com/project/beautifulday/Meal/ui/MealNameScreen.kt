package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.States.ListMealsState
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.R
import com.project.beautifulday.inicio2.jotiOne


@Composable
fun MealNameScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity){
    val meals by viewmodel.mealsData.collectAsState()
    val showOutLineText = viewmodel.showOutLineText


    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBarMeal(showOutLineText = showOutLineText, viewmodel = viewmodel)},
        bottomBar = { MyBottomBarMeal() }

    ) {
        innerPadding ->
        MyContentMeal2(
            innerPadding = innerPadding,
            viewmodel = viewmodel,
            navController = navController,
            showOutLinedText = showOutLineText,
            meals = meals
        )
    }
    viewmodel.ShowMealsName(meals = meals)
}


@Composable
fun MyContentMeal2(
    innerPadding: PaddingValues,
    viewmodel: MealViewmodel,
    navController: NavController,
    showOutLinedText: Boolean,
    meals: List<MealState>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue))
            .padding(innerPadding),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (showOutLinedText) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = viewmodel.mealName,
                            onValueChange = { viewmodel.changeMealName(it) },
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
                                })
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.electricBlue)),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.inicio2_nube),
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
                modifier = Modifier.padding(top = 20.dp)
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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
}
