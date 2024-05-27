package com.project.beautifulday.Meal.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.Components.CreateDialog
import com.project.beautifulday.Meal.ui.Components.MyOutlinedTectField
import com.project.beautifulday.Meal.ui.Components.createAlert
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel

@Composable
fun CreateNewMeal(navController: NavController, viewmodel: MealViewmodel, viewmodelA: ViewmodelAplication, context: ComponentActivity, cocktailViewmodel: CocktailViewmodel){

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

    Column {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.electricBlue)),
            contentAlignment = Alignment.TopCenter
        ){
            Column(

            ) {
                /*
                Text(
                    text = "Beautiful",
                    fontSize = 32.sp,
                    fontFamily = jotiOne,
                    color = colorResource(id = R.color.selectiveYellow),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                 */
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
                        imeAction = ImeAction.Done
                    )
                )


                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = name,
                    onValueChange = viewmodelA::changeName, //Equivalente a {newName -> viewmodelA.changeName(newName)}
                    focusRequester = focusRequester,
                    label = "Nombre"
                )



                /*
                OutlinedTextField(
                    value = name, onValueChange = { viewmodelA.changeName(it) },
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
                    label = { Text(text = "Nombre") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                 */

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = descripcion,
                    onValueChange = viewmodelA::changeDescripcion,
                    focusRequester = focusRequester,
                    label = "Descripción"
                )

                /*
                OutlinedTextField(
                    value = descripcion, onValueChange = { viewmodelA.changeDescripcion(it) },
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
                    label = { Text(text = "Descripción") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                 */

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = ingrediente,
                    onValueChange = viewmodelA::changeIngrediente,
                    focusRequester = focusRequester,
                    label = "Ingredientes"
                )

                /*
                OutlinedTextField(
                    value = ingrediente, onValueChange = { viewmodelA.changeIngrediente(it) },
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
                    label = { Text(text = "Ingredientes") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                 */

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = foto,
                    onValueChange = viewmodelA::changeUriFoto,
                    focusRequester = focusRequester,
                    label = "Foto"
                )

                /*
                OutlinedTextField(
                    value = foto, onValueChange = { viewmodelA.changeUriFoto(it) },
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
                    label = { Text(text = "foto") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                 */

                Spacer(modifier = Modifier.padding(2.dp))

                MyOutlinedTectField(
                    value = video,
                    onValueChange = viewmodelA::changeUriVideo,
                    focusRequester = focusRequester,
                    label = "Video"
                )

                /*
                OutlinedTextField(
                    value = video, onValueChange = { viewmodelA.changeUriVideo(it) },
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
                    label = { Text(text = "video") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                 */
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
                /*
                Text(
                    text = "DAY",
                    fontFamily = jotiOne,
                    fontSize = 32.sp,
                    color = colorResource(id = R.color.electricBlue),
                    modifier = Modifier.padding(10.dp)
                )

                 */
                AsyncImage(model = R.drawable.logo, contentDescription = null)
                Spacer(modifier = Modifier.weight(1f))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom){
                    Text(text = "Buscar foto y video", modifier = Modifier
                        .padding(10.dp)
                        .clickable { navController.navigate("camera") },
                        color = colorResource(id = R.color.paynesGray))
                    Text(text = "Enviar datos", modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            createAlert(navController, viewmodelA, viewmodel, cocktailViewmodel, context)

                            /*
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
                                mutableListOf()
                            )
                            viewmodel.saveNewMeals("CreateMeals", context) {
                                Toast
                                    .makeText(
                                        context,
                                        "Receta guardada correctamente",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                                viewmodelA.clean()
                                navController.navigate("principal")

                            }

                             */


                        },
                        color = colorResource(id = R.color.paynesGray))

                }
            }
        }

        Spacer(modifier = Modifier.weight(2f))

        if (showCreateAlert) {
            CreateDialog(
                showAlert = showAlert,
                tittle = "Aviso",
                text = "Tiene registros sin rellenar\n¿Desea seguir?",
                onDismiss = { viewmodelA.changeAlert(!showAlert) }) {
                if(screen == "meals"){
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
                        mutableListOf()
                    )
                    viewmodel.saveNewMeals("CreateMeals", context) {
                        Toast
                            .makeText(
                                context,
                                "Receta guardada correctamente",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        viewmodelA.clean()
                        navController.navigate("principal")

                        viewmodelA.changeAlert(false)

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
                        strMedia = video
                    )

                    cocktailViewmodel.saveNewCocktail("CreateCocktails", context) {
                        Toast
                            .makeText(
                                context,
                                "Cocktail guardado correctamente",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        viewmodelA.clean()
                        navController.navigate("principal")

                        viewmodelA.changeAlert(false)

                    }
                }
            }
        }


    }
}