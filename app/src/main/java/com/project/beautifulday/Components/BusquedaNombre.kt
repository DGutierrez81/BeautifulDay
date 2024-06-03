package com.project.beautifulday.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.R
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication


@Composable
fun BusquedaNombre(
    navController: NavController,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    showOutLineText: Boolean,
    cocktailViewmodel: CocktailViewmodel
){
    val screen = viewmodelA.screen
    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center){
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(screen == "meal"){
                OutlinedTextField(value = viewmodel.mealName, onValueChange = { viewmodel.changeMealName(it)},
                    label = { Text(text = "Busqueda por nombre") },
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = Color.Transparent),
                    shape = RoundedCornerShape(100.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {viewmodel.getMealName(viewmodel.mealName)
                        navController.navigate("mealNameScreen")
                        viewmodel.changeshowOutLineText(showOutLineText)
                        viewmodelA.clean()
                        viewmodel.changeMealName("")})
                )
                AsyncImage(model = R.drawable.logo,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {
                            viewmodel.getMealName(viewmodel.mealName)
                            navController.navigate("mealNameScreen")
                            viewmodel.changeshowOutLineText(showOutLineText)
                            viewmodelA.clean()
                            viewmodel.changeMealName("")
                        }
                )
            }else{
                OutlinedTextField(value = cocktailViewmodel.nameCocktail, onValueChange = { cocktailViewmodel.changeNameCocktail(it)},
                    label = { Text(text = "Busqueda por nombre") },
                    modifier = Modifier
                        .padding(5.dp)
                        .background(color = Color.Transparent),
                    shape = RoundedCornerShape(100.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        cocktailViewmodel.getName(cocktailViewmodel.nameCocktail)
                        navController.navigate("listaCocktailsApi")
                        viewmodel.changeshowOutLineText(showOutLineText)
                        cocktailViewmodel.changeNameCocktail("")
                        cocktailViewmodel.clean()
                    })
                )
                AsyncImage(model = R.drawable.logo,
                    contentDescription = null,
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clickable {
                            cocktailViewmodel.getName(cocktailViewmodel.nameCocktail)
                            navController.navigate("listaCocktailsApi")
                            viewmodel.changeshowOutLineText(showOutLineText)
                            cocktailViewmodel.changeNameCocktail("")
                            cocktailViewmodel.clean()
                        }
                )
            }

        }
    }
}