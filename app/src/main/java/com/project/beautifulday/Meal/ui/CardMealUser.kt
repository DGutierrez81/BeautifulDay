package com.project.beautifulday.Meal.ui

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Components.ActionTransalate
import com.project.beautifulday.Components.CreateDialog
import com.project.beautifulday.Components.RatingBar
import com.project.beautifulday.Components.RatingBarImage
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.androidsmall1.jotiOne


/**
 * Composable para mostrar la tarjeta de detalles de una comida para el usuario.
 * Esta función recibe varios parámetros necesarios para mostrar la información de la comida y controlar las interacciones del usuario.
 *
 * @param navController NavController para manejar la navegación en la aplicación.
 * @param viewmodel Instancia del ViewModel relacionado con la información de la comida.
 * @param context ComponentActivity para acceder al contexto de la aplicación.
 * @param viewmodelA ViewModel de la aplicación para manejar ciertas acciones y estados de la aplicación.
 * @param Idoc ID del documento de la comida.
 * @param colec Nombre de la colección que contiene la comida.
 */
@Composable
fun CardMealUser(
    navController: NavController,
    viewmodel: MealViewmodel,
    context: ComponentActivity,
    viewmodelA: ViewmodelAplication,
    Idoc: String,
    colec: String
) {
    // Observa y obtiene el estado actual de la acción de traducción
    val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    // Obtiene el estado actual de la vista
    val state = viewmodelA.state.value
    // Observa y obtiene el estado actual del deslizamiento
    val slide by viewmodelA.slide.observeAsState(false)
    // Observa y obtiene el estado actual de mostrar la alerta
    val showAlert = viewmodelA.showAlert
    // Observa y obtiene el estado actual de mostrar los votos
    val showVotes = viewmodel.showVotes
    // Obtiene la calificación actual
    val currentRating = viewmodel.currentRating
    // Observa y obtiene el estado actual del progreso
    val progress by viewmodel.progress.observeAsState(true)
    val screen = viewmodelA.screen


    // Realiza acciones al lanzar el efecto
    LaunchedEffect(key1 = true) {
        viewmodel.getMealUserById(Idoc, colec)
        viewmodelA.getEmail()
    }

    // Obtiene los datos de la comida y el correo electrónico del usuario
    val meal = viewmodel.meal
    val email = viewmodelA.email

    // Diseño de la tarjeta de la comida
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la comida
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
        // Contenedor de la imagen y la información de la comida
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.paynesGray))
            ) {
                // Muestra un indicador de progreso mientras se carga la imagen
                if (progress) {
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
                                text = "Cargando" + viewmodelA.getAnimatedDots(progress),
                                color = colorResource(id = R.color.silver)
                            )
                        }
                    }
                } else {
                    // Muestra la imagen de la comida si la carga ha finalizado
                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // Muestra la calificación promedio y el número de votos
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val average = viewmodel.calculateAverage(meal.votes ?: 0, meal.points ?: 0.0)
                    if (colec == "Create $screen") {
                        Box(
                            modifier = Modifier.width(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            RatingBarImage(rating = average)
                        }
                        Text(
                            text = meal.votes.toString() + " votos",
                            color = colorResource(id = R.color.silver),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }
                }

                // Muestra los ingredientes y las instrucciones de la receta
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        ActionTransalate(
                            actionTranslate = actionTranslate,
                            text = "Ingredients:" + "\n" + meal.strIngredients?.joinToString() + "\n" + ":Instructions:" + "\n" + meal.strInstructions,
                            viewmodelA = viewmodelA,
                            context = context,
                            state = state
                        )
                    }
                }

                // Muestra el nombre del usuario que subió la receta
                if (colec == "CreateMeals") {
                    Text(
                        text = "Receta subida por\n${meal.nameUser}",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.silver)
                    )
                }
            }
        }
    }

    // Controles de la barra lateral
    Column {
        // Icono para deslizar la barra lateral
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

        // Contenido de la barra lateral
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
                // Opciones de la barra lateral
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
                Text(
                    text = "Ver video",
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            if (colec == "Create $screen") {
                                viewmodelA.changeUriVideo(meal.strYoutube ?: "")
                                navController.navigate("video")
                            } else {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(meal.strYoutube)
                                    )
                                )
                            }
                            viewmodelA.changeSlide(slide)
                        },
                    color = colorResource(id = R.color.paynesGray)
                )

                // Opción de borrar la receta, solo visible si el usuario es el mismo que la subió
                if (email.equals(meal.emailUser)) {
                    Text(
                        text = "Modificar",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                viewmodelA.changeSlide(slide)
                                viewmodel.changeUpdateMeal(true)
                                viewmodel.changeIdoc(Idoc)
                                navController.navigate("createNewMeal")
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Text(
                        text = "Borrar",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                viewmodelA.changeMessConfirm("Receta borrada correctamenete")
                                viewmodelA.changeSlide(slide)
                                viewmodelA.changeAlert(!showAlert)
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )
                } else {
                    // Opción de votar la receta
                    Text(
                        text = "Votar",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                var flag = true
                                meal.listVotes?.forEach { emailList ->
                                    if (emailList == email) {
                                        viewmodelA.changeSlide(slide)
                                        flag = false
                                        Toast.makeText(context, "Solo puede votar una vez", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                if (flag) {
                                    viewmodelA.clean()
                                    viewmodel.changeShowVotes(!showVotes)
                                }
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )
                }

                // Opción de regresar atrás
                Text(
                    text = "Atras",
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            navController.popBackStack()
                            viewmodelA.clean()
                        },
                    color = colorResource(id = R.color.paynesGray)
                )
            }
        }
    }

    // Diálogo para confirmar la eliminación de la receta
    CreateDialog(
        showAlert = showAlert,
        tittle = "Aviso",
        text = "¿Desea borrar el registro?",
        onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
        viewmodelA.deleteRegister(Idoc, colec, {navController.navigate("ok")}) { navController.navigate("meal") }
        viewmodelA.changeAlert(!showAlert)
    }

    // Diálogo para votar la receta
    if (showVotes) {
        Dialog(onDismissRequest = { viewmodel.changeShowVotes(false) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.paynesGray)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    // Barra de votación
                    RatingBar(
                        rating = currentRating,
                        onRatingChanged = { newRating ->
                            viewmodel.changeCurrentRating(newRating)
                            viewmodel.updateListVotes(newRating)
                        }
                    )

                    // Calcular la media de todos los votos
                    viewmodel.calculateAverageRating()

                    // Botón para votar
                    Column {
                        OutlinedButton(
                            onClick = {
                                viewmodel.changeIdoc(Idoc)
                                viewmodel.changeValueVotes(currentRating, "puntuacion","")
                                viewmodel.changeValueVotes(1.0, "votes","")
                                viewmodel.changeValueVotes(0.0, "listVotes", email)
                                viewmodel.updateStars(Idoc) { navController.popBackStack() }
                                viewmodel.cleanVotes()
                                viewmodel.changeShowVotes(false)
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            border = BorderStroke(2.dp, colorResource(id = R.color.paynesGray)),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.silver))
                        ) {
                            Text(text = "Votar", color = colorResource(id = R.color.paynesGray))
                        }
                    }
                }
            }
        }
    }
}


