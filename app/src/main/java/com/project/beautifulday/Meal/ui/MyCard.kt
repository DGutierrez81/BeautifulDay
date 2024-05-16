package com.project.beautifulday.Meal.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.States.MealUser
import com.project.beautifulday.Meal.ui.States.Traduction
import com.project.beautifulday.R
import com.project.beautifulday.inicio2.jotiOne
import kotlin.math.round


@Composable
fun MyCard(navController: NavController, viewmodel: MealViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication) {
    val meal = viewmodel.meal
    val actionTranslate = viewmodelA.actionTranslate
    val state = viewmodelA.state.value
    val slide = viewmodelA.slide


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
                            text = "Ingredientes:" + "\n" + meal.strIngredients.joinToString() + "\n" + ":Instructions:" + "\n" + meal.strInstructions,
                            viewmodelA = viewmodelA,
                            context = context,
                            state = state
                        )
                        /*
                        //Text(text = (meal.strIngredients.joinToString() + "/n" + meal.strInstructions), color = colorResource(id = R.color.silver), fontFamily = jotiOne)
                        if (actionTranslate) {

                            Text(
                                text = ("Ingredients:"),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                color = colorResource(id = R.color.silver),
                                fontFamily = jotiOne,
                                textAlign = TextAlign.Center
                            )

                            viewmodelA.onTextToBeTranslatedChange(
                                ("Ingredients:")
                            )
                            viewmodelA.onTranslateButtonClick(
                                text = state.textToBeTranslate,
                                context = context
                            )
                        } else {
                            state.translatedText.let {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    color = colorResource(id = R.color.silver),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        if (actionTranslate) {

                            Text(
                                text = (meal.strIngredients.joinToString()),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                color = colorResource(id = R.color.silver),
                                fontFamily = jotiOne,
                                textAlign = TextAlign.Center
                            )

                            viewmodelA.onTextToBeTranslatedChange(
                                ("Ingredients:" + "\n" + meal.strIngredients.joinToString() + "\n" + "Instructions:" + "\n" + meal.strInstructions)
                            )
                            viewmodelA.onTranslateButtonClick(
                                text = state.textToBeTranslate,
                                context = context
                            )
                        } else {
                            state.translatedText.let {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    color = colorResource(id = R.color.silver),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        */
                    }
                }

            }

        }
    }

    Column {
        Icon(painter = painterResource(id = R.drawable.density),
            contentDescription = null,
            tint = colorResource(
                id = R.color.silver
            ),
            modifier = Modifier.clickable {
                viewmodelA.changeSlide(slide)
            })

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
                    .clickable { viewmodelA.changeActionTranslate(!actionTranslate) },
                    color = colorResource(id = R.color.paynesGray))
                Text(text = "Guardar", modifier = Modifier
                    .padding(2.dp)
                    .clickable { },
                    color = colorResource(id = R.color.paynesGray))
                Text(text = "Borrar", modifier = Modifier
                    .padding(2.dp)
                    .clickable { },
                    color = colorResource(id = R.color.paynesGray))
            }
        }
    }

}

@Composable
fun ActionTransalate(actionTranslate: Boolean, text: String, viewmodelA: ViewmodelAplication, context: ComponentActivity, state: Traduction){
    if (actionTranslate) {

        Text(
            text = (text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = colorResource(id = R.color.silver),
            textAlign = TextAlign.Center
        )

        viewmodelA.onTextToBeTranslatedChange(text)
        viewmodelA.onTranslateButtonClick(
            text = state.textToBeTranslate,
            context = context
        )
    } else {
        state.translatedText.let { translatedText ->
            val processedText = translatedText.toList().joinToString(separator = "") { if (it == ':') "\n" else it.toString() }
            Text(
                text = processedText,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                color = colorResource(id = R.color.silver),
                textAlign = TextAlign.Center
            )
        }
    }
}


