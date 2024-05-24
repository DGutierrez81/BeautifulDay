package com.project.beautifulday.Meal.ui.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.Meal.ui.ViewModels.MealViewmodel
import com.project.beautifulday.Meal.ui.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne


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
    viewmodelA: ViewmodelAplication,
    showDialog: Boolean
){
    if(showMenu){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.electricBlue))
        ) {

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
                                            viewmodel.getListCategories()
                                            viewmodelA.changeSlide(slide)
                                            viewmodelA.chageShowDialog(showDialog)
                                        },
                                    color = colorResource(id = R.color.paynesGray),
                                    fontFamily = jotiOne
                                )
                                Text(
                                    text = "Paises", modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            viewmodel.getListArea()
                                            viewmodelA.changeSlide(slide)
                                            viewmodelA.chageShowDialog(showDialog)
                                            //navController.navigate("listCategory")
                                        },
                                    color = colorResource(id = R.color.paynesGray),
                                    fontFamily = jotiOne
                                )
                                Text(
                                    text = "Ingredientes", modifier = Modifier
                                        .padding(2.dp)
                                        .clickable {
                                            viewmodel.getListIngredient()
                                            navController.navigate("cardIngredient")
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
                            text = "Almacen",
                            fontFamily = jotiOne,
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable { navController.navigate("listMealUser") }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Ver recetas socios",
                            fontFamily = jotiOne,
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable { navController.navigate("listMealUserCreater") }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Crear receta",
                            fontFamily = jotiOne,
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable { navController.navigate("createNewMeal") }
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