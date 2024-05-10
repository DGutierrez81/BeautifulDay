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
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import com.project.beautifulday.R
import com.project.beautifulday.inicio2.jotiOne

@Composable
fun PrincipalScreen(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity){

    Scaffold(
        modifier = Modifier.background(colorResource(id = R.color.electricBlue)),
        topBar = { MyTopBar() },
        bottomBar = { MyBottomBar() }
    ) { innerPadding -> MyContent(innerPadding, navController)}
}

@Composable
fun MyTopBar(){
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

@Composable
fun MyBottomBar(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.selectiveYellow))
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "DAY",
                fontFamily = jotiOne,
                fontSize = 32.sp,
                color = colorResource(id = R.color.electricBlue),
                modifier = Modifier.padding(50.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
            }
        }
    }
}

@Composable
fun MyContent(innerPadding: PaddingValues, navController: NavController) {
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
                modifier = Modifier.padding(top = 20.dp)
            )
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = R.drawable.olla, contentDescription = "olla",
                            modifier = Modifier.height(50.dp)
                                .clickable { navController.navigate("meal")}
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
            }
        }
    }
}
