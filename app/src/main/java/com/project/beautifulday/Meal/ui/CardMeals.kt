package com.project.beautifulday.Meal.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.Composable
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
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne



/**
 * Función composable que muestra una tarjeta de comida.
 * Esta tarjeta incluye el nombre, la imagen, los ingredientes y las instrucciones de una comida.
 * También incluye un indicador de carga animado mientras se están obteniendo los datos.
 * Además, proporciona acciones para traducir las instrucciones, guardar la comida, ver un video y navegar hacia atrás.
 *
 * @param navController NavController para manejar la navegación.
 * @param viewmodel MealViewmodel para obtener y gestionar los datos de la comida.
 * @param context Contexto de la actividad actual para mostrar mensajes Toast.
 * @param viewmodelA ViewmodelAplication para gestionar el estado relacionado con la UI.
 * @param LgViewModel LogViewmodel para gestionar el estado de inicio de sesión y acciones relacionadas.
 */
@Composable
fun CardMeals(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel) {
    // Observando varias variables de estado de los viewmodels.
    val meal = viewmodel.meal
    val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    val state = viewmodelA.state.value
    val slide by viewmodelA.slide.observeAsState(false)
    val login = LgViewModel.login
    val progrees by viewmodel.progress.observeAsState(true)
    val mealState = viewmodel.mealsData

    // Columna principal que contiene el contenido de la tarjeta de comida.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Muestra el nombre de la comida.
        Text(
            text = meal.strMeal ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fontFamily = jotiOne,
            fontSize = 24.sp,
            color = colorResource(id = R.color.silver),
            textAlign = TextAlign.Center
        )

        // Tarjeta que contiene la imagen y los detalles de la comida.
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.paynesGray))
            ) {

                // Muestra un indicador de carga si los datos están siendo obtenidos.
                if(progrees) {
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
                                text = "Cargando" + viewmodelA.getAnimatedDots(progrees),
                                color = colorResource(id = R.color.silver)
                            )
                        }
                    }
                } else {
                    // Muestra la imagen de la comida cuando los datos están listos.
                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Lista que muestra los ingredientes y las instrucciones de la comida.
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        // Componente personalizado para traducir el texto si es necesario.
                        ActionTransalate(
                            actionTranslate = actionTranslate,
                            text = "Ingredients:" + "\n" + meal.strIngredients?.joinToString() + "\n" + ":Instructions:" + "\n" + meal.strInstructions,
                            viewmodelA = viewmodelA,
                            context = context,
                            state = state
                        )
                    }
                }
            }
        }
    }

    // Columna que contiene iconos de acciones adicionales.
    Column {
        // Icono que permite cambiar el estado de la vista deslizante.
        Icon(
            painter = painterResource(id = R.drawable.density),
            contentDescription = null,
            tint = colorResource(id = R.color.silver),
            modifier = Modifier
                .clickable {
                    viewmodelA.changeSlide(slide)
                }
                .padding(start = 5.dp, top = 8.dp)
        )

        // Visibilidad animada para mostrar las opciones adicionales cuando `slide` es verdadero.
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
                // Opción para traducir el texto.
                Text(
                    text = "Traducir",
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeActionTranslate(!actionTranslate)
                            viewmodelA.changeSlide(slide)
                        },
                    color = colorResource(id = R.color.paynesGray)
                )

                // Opción para guardar la comida si el usuario está logueado.
                if(login) {
                    Text(
                        text = "Guardar",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                viewmodel.saveNewMeals("Meals", context, {navController.navigate("ok")}) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Comida guardada correctamente",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                    viewmodelA.clean()
                                    navController.navigate("meal")
                                }
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )

                    // Opción para ver el video de la receta en YouTube.
                    Text(
                        text = "Ver video",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(meal.strYoutube)
                                    )
                                )
                                viewmodelA.changeSlide(slide)
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )
                }

                // Opción para volver atrás.
                Text(
                    text = "Atras",
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeSlide(slide)
                            viewmodelA.clean()
                            navController.popBackStack()
                        },
                    color = colorResource(id = R.color.paynesGray)
                )
            }
        }
    }
}
