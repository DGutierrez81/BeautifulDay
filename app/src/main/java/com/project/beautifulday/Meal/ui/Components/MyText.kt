package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne

@Composable
fun Mytext(
    text: String,
    modifier: () -> Unit
){
    Text(text = text,
        fontFamily = jotiOne,
        color = colorResource(id = R.color.paynesGray),
        modifier = Modifier.clickable { modifier()})
}
