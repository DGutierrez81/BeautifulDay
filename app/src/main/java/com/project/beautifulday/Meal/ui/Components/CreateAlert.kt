package com.project.beautifulday.Meal.ui.Components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.project.beautifulday.Meal.ui.ViewModels.MealViewmodel
import com.project.beautifulday.Meal.ui.ViewModels.ViewmodelAplication

fun createAlert(navController: NavController , viewmodelA: ViewmodelAplication, viewmodel: MealViewmodel, context: ComponentActivity){
    val name = viewmodelA.name
    val id = viewmodelA.id
    val descripcion = viewmodelA.descripcion
    var foto = viewmodelA.uriFoto
    val video = viewmodelA.uriVideo
    val ingrediente = viewmodelA.ingrediente
    val lista = mutableListOf(name, id, descripcion, foto, video, ingrediente)
    var confi = false



        for(i in lista){
            if(i == "") viewmodelA.changeAlert(true) else confi = true
        }

        if(confi){
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
        }
    }


