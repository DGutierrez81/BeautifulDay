package com.project.beautifulday.Components

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
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne
import com.project.beautifulday.ViewModels.CocktailViewmodel


@Composable
fun MyTopBar(
    showMenu: Boolean,
    viewmodel: MealViewmodel,
    showOutLineText: Boolean,
    cocktailViewmodel: CocktailViewmodel,
    login: Boolean,
    mealName: String,
    navController: NavController,
    slide: Boolean,
    viewmodelA: ViewmodelAplication,
    showDialog: Boolean
){
    val screen = viewmodelA.screen
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
                    Mytext(text = "Busqueda por nombre") {
                        viewmodel.changeshowOutLineText(
                            showOutLineText
                        )
                    }
                    if(screen == "meal"){

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
                            Mytext(text = "Categorias") { viewmodelA.changeSlide(slide) }

                            AnimatedVisibility(
                                visible = slide
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .background(colorResource(id = R.color.electricBlue))
                                ) {
                                    Mytext(text = "Categoria") {
                                        viewmodel.getListCategories()
                                        viewmodelA.changeSlide(slide)
                                        viewmodelA.chageShowDialog(showDialog)
                                    }

                                    Mytext(text = "Paises") {
                                        viewmodel.getListArea()
                                        viewmodelA.changeSlide(slide)
                                        viewmodelA.chageShowDialog(showDialog)
                                    }
                                }
                            }
                        }
                        if(login){
                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Favoritos") { navController.navigate("listMealUser") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Ver recetas socios") { navController.navigate("listMealUserCreater") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear receta") { navController.navigate("createNewMeal")  }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear local") { navController.navigate("createNewLocal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Donde comer") { navController.navigate("listLocal")  }

                        }
                    }else {

                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Random",
                            fontFamily = jotiOne,
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable {
                                cocktailViewmodel.getRandom()
                                navController.navigate("cardCocktails")
                            }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))

                        Column {
                            Mytext(text = "Categorias") { viewmodelA.changeSlide(slide) }

                            AnimatedVisibility(
                                visible = slide
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .background(colorResource(id = R.color.electricBlue))
                                ) {
                                    Mytext(text = "Ordinarias") {
                                        cocktailViewmodel.getCategory("Ordinary_Drink")
                                        viewmodelA.changeSlide(slide)
                                        navController.navigate("listaCocktailsApi")
                                    }

                                    Mytext(text = "Cocktails") {
                                        cocktailViewmodel.getCategory("Cocktail")
                                        viewmodelA.changeSlide(slide)
                                        navController.navigate("listaCocktailsApi")
                                    }


                                    Mytext(text = "Alcoholicas") {
                                        cocktailViewmodel.getAlcoholics("Alcoholic")
                                        viewmodelA.changeSlide(slide)
                                        navController.navigate("listaCocktailsApi")
                                    }

                                    Mytext(text = "Sin alcohol") {
                                        cocktailViewmodel.getAlcoholics("Non_Alcoholic")
                                        viewmodelA.changeSlide(slide)
                                        navController.navigate("listaCocktailsApi")
                                    }
                                }
                            }
                        }
                        if(login){
                            Spacer(modifier = Modifier.padding(20.dp))
                            Mytext(text = "Favoritos") { navController.navigate("listCocktailUser") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Ver cocktail socios") { navController.navigate("listCocktailUserCreater") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear cocktail") { navController.navigate("createNewMeal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear local") { navController.navigate("createNewLocal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Donde beber") { navController.navigate("listLocal")  }

                        }
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

