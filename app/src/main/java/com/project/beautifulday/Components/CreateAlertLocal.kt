package com.project.beautifulday.Components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication


/**
 * Crea una alerta local para verificar si todos los campos necesarios están completos antes de guardar un registro.
 *
 * @param navController Controlador de navegación.
 * @param viewmodelA ViewModel de ViewmodelAplication.
 * @param viewmodel ViewModel de Meal.
 * @param cocktailViewmodel ViewModel de Cocktail.
 * @param user Nombre de usuario.
 * @param context Contexto de la actividad.
 */
fun createAlertLocal(navController: NavController, viewmodelA: ViewmodelAplication, viewmodel: MealViewmodel, cocktailViewmodel: CocktailViewmodel, user: String, context: ComponentActivity){
    val name = viewmodelA.name
    val descripcion = viewmodelA.descripcion
    var foto = viewmodelA.uriFoto
    val screen = viewmodelA.screen
    val pais = viewmodelA.pais
    val ciudad = viewmodelA.ciudad
    val web = viewmodelA.web
    val savedLocation = viewmodelA.savedLocation.value
    val lista = mutableListOf(name, descripcion, foto, web)
    var confi = false

    for(i in lista){
        if(i == "") {
            viewmodelA.changeCreateAlerte(true)
            confi = false
            break
        } else confi = true
    }

    if(confi){
        viewmodelA.newLocal(name, foto, comentario = descripcion, pais= pais,ciudad = ciudad, nameUser = user,"https://$web", savedLocation)
        viewmodelA.saveNewLocal(
            colec = "Locales $screen",
            context = context,
            { navController.navigate("ok") }) {
            Toast
                .makeText(
                    context,
                    "Local guardado correctamente",
                    Toast.LENGTH_SHORT
                )
                .show()
            viewmodelA.clean()
            navController.popBackStack()
        }
    }
}
