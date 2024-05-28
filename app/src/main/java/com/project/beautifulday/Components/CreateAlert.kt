package com.project.beautifulday.Components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication

fun createAlert(navController: NavController, viewmodelA: ViewmodelAplication, viewmodel: MealViewmodel, cocktailViewmodel: CocktailViewmodel, context: ComponentActivity){
    val name = viewmodelA.name
    val id = viewmodelA.id
    val descripcion = viewmodelA.descripcion
    var foto = viewmodelA.uriFoto
    val video = viewmodelA.uriVideo
    val screen = viewmodelA.screen
    val ingrediente = viewmodelA.ingrediente
    val lista = mutableListOf(name, id, descripcion, foto, video, ingrediente)
    var confi = false



        for(i in lista){
            if(i == "") viewmodelA.changeCreateAlerte(true) else confi = true
        }

        if(confi){
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
                    mutableListOf()
                )
                viewmodel.saveNewMeals("CreateMeals", context) {
                    Toast
                        .makeText(
                            context,
                            "Registro guardado correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("principal")

                }
            }else{
                cocktailViewmodel.SaveCocktail(
                    id,
                    name,
                    descripcion,
                    foto,
                    ingrediente
                        .split(",", " ")
                        .toMutableList(),
                    video,

                )
                cocktailViewmodel.saveNewCocktail("CreateCocktails", context) {
                    Toast
                        .makeText(
                            context,
                            "Registro guardado correctamente",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    viewmodelA.clean()
                    navController.navigate("principal")

                }
            }
        }
    }


