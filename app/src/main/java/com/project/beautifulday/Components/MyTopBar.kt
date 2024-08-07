package com.project.beautifulday.Components

import androidx.activity.ComponentActivity
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
import com.project.beautifulday.ViewModels.LogViewmodel


/**
 * Composición de la barra superior de la aplicación.
 *
 * @param showMenu Indica si se debe mostrar el menú.
 * @param viewmodel ViewModel de las comidas.
 * @param showOutLineText Indica si se debe mostrar el texto de contorno.
 * @param cocktailViewmodel ViewModel de los cócteles.
 * @param login Indica si el usuario está autenticado.
 * @param mealName Nombre de la comida actualmente seleccionada.
 * @param navController Controlador de navegación.
 * @param slide Indica si la vista está deslizada.
 * @param viewmodelA ViewModel de la aplicación.
 * @param showDialog Indica si se debe mostrar un cuadro de diálogo.
 */
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
    slideUser: Boolean,
    viewmodelA: ViewmodelAplication,
    logViewmodel: LogViewmodel,
    showDialog: Boolean,
    context: ComponentActivity
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
                                viewmodel.changeshowOutLineText(true)
                                viewmodelA.changeRandom(true)
                                viewmodel.getRandom()
                                navController.navigate("myCard")
                            }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))

                        Column {
                            Mytext(text = "Categorias") {
                                viewmodel.changeshowOutLineText(true)
                                viewmodelA.changeSlide(slide)
                            }

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

                            Mytext(text = "Favoritos") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listMealUser")
                            }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Ver recetas socios") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listMealUserCreater") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear receta") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("createNewMeal")  }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear local") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("createNewLocal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Donde comer") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listLocal")  }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Column {
                                Mytext(text = "Gestión usuario") {
                                    viewmodel.changeshowOutLineText(true)
                                    viewmodelA.changeSlideUser(!slideUser)  }

                                AnimatedVisibility(
                                    visible = slideUser
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(3.dp)
                                            .background(colorResource(id = R.color.electricBlue))
                                    ) {
                                        Mytext(text = "Modificar") {
                                            logViewmodel.clean()
                                            logViewmodel.fetchUser()
                                            logViewmodel.changeUpdateUser(true)
                                            navController.navigate("register")
                                            viewmodelA.clean()
                                        }

                                        Mytext(text = "Borrar") {
                                            viewmodelA.changeMessConfirm("Usuario eliminado correctamente")
                                            logViewmodel.fetchUser()
                                            viewmodelA.changeAlert(true)
                                            viewmodelA.clean()
                                        }
                                    }
                                }
                            }

                        }
                    }else {

                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Random",
                            fontFamily = jotiOne,
                            color = colorResource(id = R.color.paynesGray),
                            modifier = Modifier.clickable {
                                viewmodelA.changeRandom(true)
                                cocktailViewmodel.getRandom()
                                navController.navigate("cardCocktails")
                            }
                        )
                        Spacer(modifier = Modifier.padding(20.dp))

                        Column {
                            Mytext(text = "Categorias") {
                                viewmodel.changeshowOutLineText(true)
                                viewmodelA.changeSlide(slide) }

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
                            Mytext(text = "Favoritos") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listCocktailUser") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Ver cocktail socios") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listCocktailUserCreater") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear cocktail") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("createNewMeal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Crear local") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("createNewLocal") }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Mytext(text = "Donde beber") {
                                viewmodel.changeshowOutLineText(true)
                                navController.navigate("listLocal")  }

                            Spacer(modifier = Modifier.padding(20.dp))

                            Column {
                                Mytext(text = "Gestión usuario") {
                                    viewmodel.changeshowOutLineText(true)
                                    viewmodelA.changeSlideUser(!slideUser)  }

                                AnimatedVisibility(
                                    visible = slideUser
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(3.dp)
                                            .background(colorResource(id = R.color.electricBlue))
                                    ) {
                                        Mytext(text = "Modificar") {
                                            logViewmodel.clean()
                                            logViewmodel.fetchUser()
                                            logViewmodel.changeUpdateUser(true)
                                            navController.navigate("register")
                                            viewmodelA.clean()
                                        }

                                        Mytext(text = "Borrar") {
                                            viewmodelA.changeMessConfirm("Usuario eliminado correctamente")
                                            logViewmodel.fetchUser()
                                            viewmodelA.changeAlert(true)
                                            viewmodelA.clean()
                                        }
                                    }
                                }
                            }

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

