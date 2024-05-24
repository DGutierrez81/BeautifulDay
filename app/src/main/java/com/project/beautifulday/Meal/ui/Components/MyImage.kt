package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


@Composable
fun MyImage(model: Any?, navController: NavController, contentDescription: String, route: String, textDescription: String){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = model, contentDescription = contentDescription,
            modifier = Modifier
                .height(50.dp)
                .clickable { navController.navigate(route) }
        )
        Text(
            text = textDescription,
            fontSize = 12.sp,
            fontFamily = jotiOne,
            color = colorResource(id = R.color.paynesGray),
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}