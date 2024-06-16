package com.project.beautifulday.Components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.androidsmall1.jotiOne

/**
 * Composable para crear un nuevo registro.
 *
 * @param navController Controlador de navegación.
 * @param viewmodel ViewModel de Meal.
 * @param viewmodelA ViewModel de ViewmodelAplication.
 * @param context Contexto de la actividad.
 * @param cocktailViewmodel ViewModel de Cocktail.
 */
@Composable
fun CreateRegister(navController: NavController, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, context: ComponentActivity, cocktailViewmodel: CocktailViewmodel){

    val name = viewmodelA.name
    val descripcion = viewmodelA.descripcion
    val foto = viewmodelA.uriFoto
    val video = viewmodelA.uriVideo
    val ingrediente = viewmodelA.ingrediente
    val focusRequester = viewmodelA.focusRequest
    val screen = viewmodelA.screen
    val updateMeal = viewmodel.updateMeal
    val updateCocktails = cocktailViewmodel.updateCocktails
    val showCreateAlert = viewmodelA.showCreateAlert
    val idDocMeal = viewmodel.IdDoc
    val iDocCocktail = cocktailViewmodel.IdDoc
    val intentGalleryLancher = viewmodelA.intentGalleryLaucher()
    val intentGalleryLancheVideo = viewmodelA.intentGalleryLaucherVideo()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true){
        viewmodelA.fetchUser()
    }
    val user = viewmodelA.user

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue)),
            contentAlignment = Alignment.TopCenter
        ){
            Column(

            ) {

                Text(
                    text = "Beautiful",
                    fontSize = 32.sp,
                    fontFamily = jotiOne,
                    color = colorResource(id = R.color.selectiveYellow),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                MyOutlinedTectField(
                    value = name,
                    onValueChange = viewmodelA::changeName,
                    focusRequester = focusRequester,
                    label = "Nombre",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = descripcion,
                    onValueChange = viewmodelA::changeDescripcion,
                    focusRequester = focusRequester,
                    label = "Descripción",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )


                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = ingrediente,
                    onValueChange = viewmodelA::changeIngrediente,
                    focusRequester = focusRequester,
                    label = "Ingredientes",
                    keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                )


                Spacer(modifier = Modifier.padding(2.dp))

                Column {
                    MyOutlinedTectField(
                        value = foto,
                        onValueChange = viewmodelA::changeUriFoto,
                        focusRequester = focusRequester,
                        label = "Foto",
                        keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                    Row {
                        Text(text = "Buscar en galeria", modifier = Modifier
                            .clickable { intentGalleryLancher.launch("*/*") }
                            .padding(start = 36.dp))
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.padding(2.dp))

                Column {
                    MyOutlinedTectField(
                        value = video,
                        onValueChange = viewmodelA::changeUriVideo,
                        focusRequester = focusRequester,
                        label = "Video",
                        keyboardActions = {focusManager.moveFocus(FocusDirection.Down)}
                    )
                    Row {
                        Text(text = "Buscar en galeria", modifier = Modifier
                            .clickable { intentGalleryLancheVideo.launch("*/*") }
                            .padding(start = 36.dp))
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }

                Spacer(modifier = Modifier.padding(15.dp))
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.selectiveYellow)),
            contentAlignment = Alignment.TopCenter){
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.weight(1f))

                androidx.compose.material.Text(
                    text = "DAY",
                    fontFamily = jotiOne,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.electricBlue)
                )

                Spacer(modifier = Modifier.height(2.dp))

                AsyncImage(model = R.drawable.logo, contentDescription = null, modifier = Modifier.clickable {
                    navController.navigate("principal")
                    viewmodelA.clean()
                })
                Spacer(modifier = Modifier.weight(1f))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom){
                    Text(text = "Hacer foto y video", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            if (screen == "meal") {
                                navController.navigate("camera/${user.email}?numb=1")
                            } else {
                                navController.navigate("camera/${user.email}?numb=1")
                            }
                        },
                        color = colorResource(id = R.color.paynesGray))
                    Text(text = "Enviar datos", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            viewmodelA.changeMessConfirm("Receta guardada correctamente")
                            if (updateMeal) {
                                viewmodel.changeMeal(name, "strMeal")
                                viewmodel.changeMeal(descripcion, "strInstructions")
                                viewmodel.changeMeal(ingrediente, "strIngredients")
                                viewmodel.changeMeal(foto, "strMealThumb")
                                viewmodel.changeMeal(video, "strYoutube")
                                viewmodel.updateMeal(idDocMeal){navController.navigate("listMealUserCreater")}
                                viewmodel.changeUpdateMeal(false)
                                viewmodelA.clean()
                                viewmodel.changeIdoc("")

                            }else if (updateCocktails){
                                cocktailViewmodel.changeCocktail(name, "strDrink")
                                cocktailViewmodel.changeCocktail(descripcion, "strInstructions")
                                cocktailViewmodel.changeCocktail(ingrediente, "strList")
                                cocktailViewmodel.changeCocktail(foto, "strDrinkThumb")
                                cocktailViewmodel.changeCocktail(video, "strmedia")
                                cocktailViewmodel.updateCocktail(iDocCocktail){navController.navigate("listCocktailUserCreater")}
                                cocktailViewmodel.changeUpdateCocktail(false)
                                viewmodelA.clean()
                                cocktailViewmodel.changeIdoc("")
                            } else {
                                createAlert(
                                    navController,
                                    viewmodelA,
                                    viewmodel,
                                    cocktailViewmodel,
                                    user.userName ?: "",
                                    context
                                )
                            }
                        },
                        color = colorResource(id = R.color.paynesGray))

                }
            }
        }

        Spacer(modifier = Modifier.weight(2f))

        CreateDialog(
            showAlert = showCreateAlert,
            tittle = "Aviso",
            text = "Tiene registros sin rellenar\n¿Desea seguir?",
            onDismiss = { viewmodelA.changeCreateAlerte(!showCreateAlert) }) {
            if(screen == "meal"){
                viewmodel.saveMealCreater(
                    name,
                    "",
                    "",
                    descripcion,
                    foto,
                    "",
                    video,
                    ingrediente
                        .split(",")
                        .toMutableList(),
                    mutableListOf(),
                    user.userName
                )
                viewmodel.saveNewMeals("Create $screen", context, {navController.navigate("ok")}) {
                    Toast
                        .makeText(
                            context,
                            "Registro guardado correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("meal")
                }
            }else{
                cocktailViewmodel.SaveCocktailCreater(
                    name,
                    descripcion,
                    foto,
                    ingrediente
                        .split(",")
                        .toMutableList(),
                    video,
                    user.userName
                )
                cocktailViewmodel.saveNewCocktail("Create $screen", context, {navController.navigate("ok")}) {
                    Toast
                        .makeText(
                            context,
                            "Cocktail guardado correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("cocktail")
                }
            }
        }
    }
}
