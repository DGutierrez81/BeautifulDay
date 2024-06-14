package com.project.beautifulday.Cocktail.ui

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
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne


/**
 * Composable para mostrar los detalles de un cocktail subido por el usuario.
 *
 * @param navController Controlador de navegación.
 * @param viewmodel ViewModel de Cocktail.
 * @param context Contexto de la actividad.
 * @param viewmodelA ViewModel de ViewmodelAplication.
 * @param Idoc Identificador del cocktail.
 * @param colec Colección de cocktails.
 */
@Composable
fun CardCocktailUser(navController: NavController, viewmodel: CocktailViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, Idoc: String, colec: String) {
    val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    val state = viewmodelA.state.value
    val slide by viewmodelA.slide.observeAsState(false)
    val showAlert = viewmodelA.showAlert
    val showVotes = viewmodel.showVotes
    val currentRating = viewmodel.currentRating
    val progrees by viewmodel.progress.observeAsState(true)
    val screen = viewmodelA.screen

    LaunchedEffect(key1 = true) {
        viewmodel.getCocktailUserById(Idoc, colec)
        viewmodelA.getEmail()
    }

    val cocktail = viewmodel.cocktail
    val email = viewmodelA.email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cocktail.strDrink ?: "", modifier = Modifier
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
                        model = cocktail.strDrinkThumb,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val average = viewmodel.calculateAverage(cocktail.votes ?: 0, cocktail.puntuacion ?: 0.0)
                    if (colec == "Create $screen") {
                        Box(modifier = Modifier.width(120.dp), contentAlignment = Alignment.Center) {
                            RatingBarImage(rating = average)
                        }
                        Text(text = cocktail.votes.toString() + " votos", color = colorResource(id = R.color.silver), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
                    }
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
                            text = "Ingredientes:" + "\n" + cocktail.strList?.joinToString() + "\n" + ":Instructions:" + "\n" + cocktail.strInstructions,
                            viewmodelA = viewmodelA,
                            context = context,
                            state = state
                        )
                    }
                }

                Text(text = "Cocktail subido por\n${cocktail.nameUser}", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = colorResource(
                    id = R.color.silver
                )
                )

            }
        }
    }

    Column {
        Icon(
            painter = painterResource(id = R.drawable.density),
            contentDescription = null,
            tint = colorResource(
                id = R.color.silver
            ),
            modifier = Modifier
                .clickable {
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
                Text(text = "Traducir", modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        viewmodelA.changeActionTranslate(!actionTranslate)
                        viewmodelA.changeSlide(slide)
                    },
                    color = colorResource(id = R.color.paynesGray)
                )
                if (colec == "Create $screen") {
                    Text(text = "Ver video", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeUriVideo(cocktail.strmedia ?: "")
                            navController.navigate("video")
                            viewmodelA.changeSlide(slide)
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )

                }
                if (email.equals(cocktail.emailUser)) {
                    Text(
                        text = "Modificar",
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                viewmodelA.changeSlide(slide)
                                viewmodel.changeUpdateCocktail(true)
                                viewmodel.changeIdoc(Idoc)
                                navController.navigate("createNewMeal")
                            },
                        color = colorResource(id = R.color.paynesGray)
                    )
                    Text(text = "Borrar", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeMessConfirm("Receta borrada correctamente")
                            viewmodelA.changeAlert(!showAlert)
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )
                } else {
                    Text(text = "Votar", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            var flag = true
                            cocktail.listVotes?.forEach { emailList ->
                                if (emailList == email) {
                                    viewmodelA.changeSlide(slide)
                                    flag = false
                                    Toast
                                        .makeText(
                                            context,
                                            "Solo puede votar una vez",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                            if (flag) {
                                viewmodelA.changeSlide(slide)
                                viewmodel.changeShowVotes(!showVotes)
                            }
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
                Text(
                    text = "Atras", modifier = Modifier
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

    CreateDialog(showAlert = showAlert, tittle = "Aviso", text = "¿Desea borrar el registro?", onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
        viewmodelA.deleteRegister(Idoc, colec, {navController.navigate("ok")}) { navController.navigate("cocktail") }
        viewmodelA.changeSlide(slide)
        viewmodelA.changeAlert(!showAlert)
    }

    if (showVotes) {
        Dialog(onDismissRequest = { viewmodel.changeShowVotes(false) }) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.paynesGray)),
                contentAlignment = Alignment.Center) {
                Column {
                    RatingBar(
                        rating = currentRating,
                        onRatingChanged = { newRating ->
                            viewmodel.changeCurrentRating(newRating)
                            viewmodel.updateListVotes(newRating)
                        }
                    )

                    viewmodel.calculateAverageRating()

                    Column {

                        OutlinedButton(onClick = {
                            viewmodel.changeValueVotes(currentRating, "puntuacion", "")
                            viewmodel.changeValueVotes(1.0, "votes", "")
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
