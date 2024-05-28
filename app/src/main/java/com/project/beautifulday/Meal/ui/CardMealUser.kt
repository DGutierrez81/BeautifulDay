package com.project.beautifulday.Meal.ui

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.androidsmall1.jotiOne

@Composable
fun CardMealUser(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, Idoc: String, colec: String){
    val actionTranslate = viewmodel.actionTranslate
    //val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    val state = viewmodelA.state.value
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showAlert = viewmodelA.showAlert
    val login = LgViewModel.login
    val showVotes = viewmodel.showVotes
    var currentRating = viewmodel.currentRating

    LaunchedEffect(key1 = true){
        viewmodel.getMealUserById(Idoc, colec)
    }

    val meal = viewmodel.meal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.paynesGray)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = meal.strMeal ?: "", modifier = Modifier
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

                AsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                LazyColumn(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {

                        ActionTransalate(
                            actionTranslate = actionTranslate,
                            text = "Ingredientes:" + "\n" + meal.strIngredients?.joinToString() + "\n" + ":Instructions:" + "\n" + meal.strInstructions,
                            viewmodelA = viewmodelA,
                            context = context,
                            state = state
                        )
                    }
                }

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
                Text(text = "Traducir", modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        viewmodel.changeActionTranslate(!actionTranslate)
                        viewmodelA.changeSlide(slide)
                    },
                    color = colorResource(id = R.color.paynesGray)
                )
                if(colec == "CreateMeals"){
                    Text(text = "Ver video", modifier = Modifier
                        .padding(2.dp)
                        .clickable {
                            viewmodelA.changeUriVideo(meal.strYoutube ?: "")
                            navController.navigate("video")
                            viewmodelA.changeSlide(slide)
                        },
                        color = colorResource(id = R.color.paynesGray)
                    )
                }
                Text(text = "Borrar", modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        viewmodelA.changeAlert(!showAlert)
                    },
                    color = colorResource(id = R.color.paynesGray)
                )
            }
        }
    }

    CreateDialog(showAlert = showAlert  ,tittle = "Aviso", text = "¿Desea borrar el registro?", onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
        viewmodelA.deleteRegister(Idoc, colec){ navController.navigate("principal") }
        viewmodelA.changeSlide(slide)
        viewmodelA.changeAlert(!showAlert)
    }

    if (showVotes) {
        Dialog(onDismissRequest = { viewmodel.changeShowVotes(false) }) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
                contentAlignment = Alignment.Center) {
                Column {
                    RatingBar(
                        rating = currentRating,
                        onRatingChanged = { newRating ->
                            viewmodel.changeCurrentRating(newRating)
                            viewmodel.updateListVotes(newRating)
                        }
                    )

                    // Calcular la media de todos los votos
                    viewmodel.calculateAverageRating()


                    Column {

                        OutlinedButton(onClick = {
                            viewmodel.changeValueVotes(currentRating, "puntuacion")
                            viewmodel.changeValueVotes( 1.0, "votes")
                            viewmodel.updateStars(Idoc){navController.popBackStack()}
                            viewmodel.cleanVotes()
                            viewmodel.changeShowVotes(false)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                            border = BorderStroke(2.dp, colorResource(id = R.color.electricBlue))
                        ) {
                            Text(text = "Votar", color = colorResource(id = R.color.electricBlue))
                        }
                    }
                }
            }
        }

    }

}


/*
viewmodel.deleteMeal(Idoc, colec){ navController.navigate("principal") }
                        viewmodelA.changeSlide(slide)
 */