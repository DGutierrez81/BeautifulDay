package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne

//import com.project.beautifulday.inicio2.jotiOne

@Composable
fun CardIngredient(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication){
    val ingredientData by viewmodel.ingredientData.collectAsState()
    //val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    val actionTranslate = viewmodel.actionTranslate
    val state = viewmodelA.state.value
    val catagoria = viewmodel.categoria



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for(category in ingredientData){
            if(category.idIngredient == catagoria){
                Text(
                    text = category.strIngredient?: "", modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp), fontFamily = jotiOne, fontSize = 24.sp, color = colorResource(
                        id = R.color.silver
                    ), textAlign = TextAlign.Center
                )
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.paynesGray))
                    ) {

                        Text(
                            text = category.strDescription?: "", modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp), fontFamily = jotiOne, fontSize = 24.sp, color = colorResource(
                                id = R.color.silver
                            ), textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.padding(3.dp))
                        TextButton(onClick = {
                            viewmodel.changeActionTranslate(!actionTranslate)
                        }) {
                            Text(text = "Traducir", fontFamily = jotiOne, color = colorResource(id = R.color.silver))
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(200.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {

                                ActionTransalate(
                                    actionTranslate = actionTranslate,
                                    text = category.strDescription?:"",
                                    viewmodelA = viewmodelA,
                                    context = context,
                                    state = state
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}