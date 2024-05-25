package com.project.beautifulday.Meal.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.project.beautifulday.Meal.ui.States.MealState
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication
import com.project.beautifulday.R

@Composable
fun DialogArea(
    onDismiss: () -> Unit,
    lista: List<MealState>,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    navController: NavController
) {
    var category = ""
    for (titulo in lista) {
        if (titulo.strCategory != "") category = "Categorias" else category = "Paises"
    }

    AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = { /*TODO*/ },
        title = { Text(text = category) },
        text = {
            LazyColumn() {
                items(lista) { item ->
                    Text(
                        text = item.strCategory ?: "", modifier = Modifier.clickable {
                            //viewmodel.getMealCategory(item.strCategory?: "")
                            viewmodel.getListCategory()
                            viewmodel.changeTraducir(item.strCategory ?: "")
                            viewmodelA.clean()
                            navController.navigate("listCategory")
                        },
                        color = colorResource(id = R.color.silver)
                    )
                }
            }
        },
        icon = { AsyncImage(model = R.drawable.logo, contentDescription = null) },
        shape = RoundedCornerShape(50.dp),
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Salir", color = colorResource(id = R.color.silver))

            }
        },
        containerColor = colorResource(id = R.color.paynesGray),
        modifier = Modifier.background(Color.Transparent)
    )
}