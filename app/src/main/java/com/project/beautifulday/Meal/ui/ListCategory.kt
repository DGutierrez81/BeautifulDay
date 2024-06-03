package com.project.beautifulday.Meal.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Components.ActionTransalate
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


@Composable
fun ListCategory(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication) {

    val mealCategory by viewmodel.categoyData.collectAsState()
    val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    //val actionTranslate = viewmodelA.actionTranslate
    val state = viewmodelA.state.value
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val catagoria = viewmodel.categoria
    val progrees by viewmodel.progress.observeAsState(true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (category in mealCategory) {
            if (category.strCategory == catagoria) {
                Text(
                    text = category.strCategory ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontFamily = jotiOne,
                    fontSize = 24.sp,
                    color = colorResource(
                        id = R.color.silver
                    ),
                    textAlign = TextAlign.Center
                )
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .background(colorResource(id = R.color.paynesGray))
                    ) {
                        if (progrees) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator(color = colorResource(id = R.color.silver))
                                    Spacer(modifier = Modifier.padding(3.dp))
                                    Text(
                                        text = "Cargando" + viewmodelA.getAnimatedDots(
                                            progrees
                                        ), color = colorResource(id = R.color.silver)
                                    )
                                }
                            }
                        } else {
                            AsyncImage(
                                model = category.strCategoryThumb,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                            Spacer(modifier = Modifier.padding(3.dp))

                            LazyColumn(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(200.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                item {

                                    ActionTransalate(
                                        actionTranslate = actionTranslate,
                                        text = category.strCategoryDescription ?: "",
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


        /*
    
    LazyColumn(){
        items(mealCategory){item ->
            /*
            Column {
                Text(text = item.idIngredient?:"")
                Text(text = item.strIngredient?:"")
                Text(text = item.strType?:"")
                Text(text = item.strDescription?:"")
            }

             */

            Text(text = item.idCategory?:"")
            Text(text = item.strCategory?:"")
            AsyncImage(model = item.strCategoryThumb, contentDescription = null)
            Text(text = item.strCategoryDescription?:"")
            

        }
    }

     */

    Column {
        Icon(
            painter = painterResource(id = R.drawable.density),
            contentDescription = null,
            tint = colorResource(
                id = R.color.silver
            ),
            modifier = Modifier.clickable {
                viewmodelA.changeSlide(slide)
            }
                .padding(start = 5.dp, top = 8.dp)
        )

        AnimatedVisibility(
            visible = slide,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            Column(
                modifier = Modifier
                    .padding(3.dp)
                    .background(colorResource(id = R.color.silver))
            ) {
                Text(
                    text = "Traducir", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeActionTranslate(!actionTranslate)
                            viewmodelA.changeSlide(slide)
                        },
                    color = colorResource(id = R.color.paynesGray)
                )
                Text(
                    text = "Atras", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            //viewmodelA.changeSlide(slide)
                            //viewmodelA.getEmail()
                            viewmodelA.clean()
                            navController.popBackStack()
                        },
                    color = colorResource(id = R.color.paynesGray)
                )
            }
        }
    }

}