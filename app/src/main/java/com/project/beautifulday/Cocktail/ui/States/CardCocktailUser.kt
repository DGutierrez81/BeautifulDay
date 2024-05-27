package com.project.beautifulday.Cocktail.ui.States

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.ActionTransalate
import com.project.beautifulday.Meal.ui.Components.CreateDialog
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.LogViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.androidsmall1.jotiOne

@Composable
fun CardCocktailUser(navController: NavController, viewmodel: CocktailViewmodel, context: ComponentActivity, viewmodelA: ViewmodelAplication, LgViewModel: LogViewmodel, Idoc: String, colec: String){
    val actionTranslate = viewmodel.actionTranslate
    //val actionTranslate by viewmodelA.actionTranslate.observeAsState(true)
    val state = viewmodelA.state.value
    //val slide = viewmodelA.slide
    val slide by viewmodelA.slide.observeAsState(false)
    val showAlert = viewmodelA.showAlert
    val login = LgViewModel.login

    LaunchedEffect(key1 = true){
        //viewmodel.getMealUserById(Idoc, colec)
        viewmodel.getCocktailUserById(Idoc, colec)
    }

    val cocktail = viewmodel.cocktail

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

                AsyncImage(
                    model = cocktail.strDrinkThumb,
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
                            text = "Ingredientes:" + "\n" + cocktail.strList?.joinToString() + "\n" + ":Instructions:" + "\n" + cocktail.strInstructions,
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
                if(colec == "CreateCocktails"){
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
    }
}