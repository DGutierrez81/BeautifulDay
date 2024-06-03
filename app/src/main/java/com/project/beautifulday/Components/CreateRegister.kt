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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel

@Composable
fun CreateRegister(navController: NavController, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, context: ComponentActivity, cocktailViewmodel: CocktailViewmodel){

    val name = viewmodelA.name
    val id by viewmodelA::id
    val descripcion = viewmodelA.descripcion
    var foto = viewmodelA.uriFoto
    val video = viewmodelA.uriVideo
    val ingrediente = viewmodelA.ingrediente
    val focusRequester = viewmodelA.focusRequest
    val showAlert = viewmodelA.showAlert
    val screen = viewmodelA.screen
    val showCreateAlert = viewmodelA.showCreateAlert
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

                Spacer(modifier = Modifier.padding(5.dp))

                OutlinedTextField(
                    value = id, onValueChange = { viewmodelA.changeId(it, context) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp)
                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(22),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    label = { Text(text = "Id") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )


                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = name,
                    onValueChange = viewmodelA::changeName, //Equivalente a {newName -> viewmodelA.changeName(newName)}
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
                    Text(text = "Buscar en galeria", modifier = Modifier
                        .clickable { intentGalleryLancher.launch("*/*") }
                        .padding(horizontal = 36.dp))
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
                    Text(text = "Buscar en galeria", modifier = Modifier
                        .clickable { intentGalleryLancheVideo.launch("*/*") }
                        .padding(horizontal = 36.dp))
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

                AsyncImage(model = R.drawable.logo, contentDescription = null, modifier = Modifier.clickable {
                    viewmodelA.clean()
                    navController.popBackStack()
                })
                Spacer(modifier = Modifier.weight(1f))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom){
                    Text(text = "Hacer foto y video", modifier = Modifier
                        .padding(10.dp)
                        .clickable { navController.navigate("camera") },
                        color = colorResource(id = R.color.paynesGray))
                    Text(text = "Enviar datos", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            createAlert(
                                navController,
                                viewmodelA,
                                viewmodel,
                                cocktailViewmodel,
                                user,
                                context
                            )
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
            onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
            if(screen == "meal"){
                viewmodel.saveMeal(
                    id,
                    name,
                    "",
                    "",
                    descripcion,
                    foto,
                    "",
                    video,
                    ingrediente
                        .split(",", " ")
                        .toMutableList(),
                    mutableListOf(),
                    user
                )
                viewmodel.saveNewMeals("CreateMeals", context, {navController.navigate("ok")}) {
                    Toast
                        .makeText(
                            context,
                            "Receta guardada correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("meal")

                    viewmodelA.changeCreateAlerte(false)

                }
            }else{
                cocktailViewmodel.SaveCocktail(
                    idDrink = id,
                    strDrink = name,
                    strInstructions = descripcion,
                    strDrinkThumb = foto,
                    strList = ingrediente
                        .split(",", " ")
                        .toMutableList(),
                    strMedia = video,
                    nameUser = user
                )

                cocktailViewmodel.saveNewCocktail("CreateCocktails", context, {navController.navigate("ok")}) {
                    Toast
                        .makeText(
                            context,
                            "Cocktail guardado correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("cocktail")

                    viewmodelA.changeCreateAlerte(false)

                }
            }
        }
    }

}