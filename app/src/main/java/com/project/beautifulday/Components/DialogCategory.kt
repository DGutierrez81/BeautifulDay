package com.project.beautifulday.Components

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

/**
 * Muestra un diálogo de categoría con opciones de selección según la lista proporcionada.
 *
 * @param onDismiss La acción a realizar cuando se descarta el diálogo.
 * @param lista La lista de elementos de categoría o país a mostrar en el diálogo.
 * @param viewmodel El ViewModel asociado a la pantalla actual.
 * @param viewmodelA El ViewModel de la aplicación.
 * @param navController El controlador de navegación.
 */
@Composable
fun DialogCategory(
    onDismiss: () -> Unit,
    lista: List<MealState>,
    viewmodel: MealViewmodel,
    viewmodelA: ViewmodelAplication,
    navController: NavController
) {
    var category = ""
    var navegar = ""
    var traducir = ""
    for (titulo in lista) {
        if (titulo.strCategory != null) {
            category = "Categorias"
            navegar = "listCategory"
        } else {
            category = "Paises"
            navegar = "mealNameScreen"
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = { /*TODO*/ },
        title = { Text(text = category) },
        text = {
            LazyColumn() {
                items(lista) { item ->
                    Text(
                        text = item.strCategory ?: item.strArea ?: "",
                        modifier = Modifier.clickable {
                            viewmodel.getListCategory()
                            viewmodel.getMealArea(item.strArea ?: "")
                            viewmodel.changeTraducir(item.strCategory ?: "")
                            viewmodelA.clean()
                            navController.navigate(navegar)
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
