package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne

@Composable
fun ScreenCenter(
    navController: NavController,
    viewmodelA: ViewmodelAplication,
    LgViewModel: LogViewmodel,
    showCenter: Int
){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ){
            Icon(painter = painterResource(id = R.drawable.inicio2_nube),
                contentDescription = null,
                modifier = Modifier.padding(top = 30.dp, end = 50.dp),
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
                ViewCenter(showCenter = showCenter, navController = navController, viewmodelA, LgViewModel = LgViewModel)
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
}