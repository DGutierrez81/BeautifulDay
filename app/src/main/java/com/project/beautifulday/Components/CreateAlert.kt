package com.project.beautifulday.Components

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.navigation.NavController
import com.project.beautifulday.ViewModels.CocktailViewmodel
import com.project.beautifulday.ViewModels.MealViewmodel
import com.project.beautifulday.ViewModels.ViewmodelAplication


fun createAlert(navController: NavController, viewmodelA: ViewmodelAplication, viewmodel: MealViewmodel, cocktailViewmodel: CocktailViewmodel, user: String, context: ComponentActivity){
    val name = viewmodelA.name
    val descripcion = viewmodelA.descripcion
    var foto = viewmodelA.uriFoto
    val video = viewmodelA.uriVideo
    val screen = viewmodelA.screen
    val ingrediente = viewmodelA.ingrediente
    val lista = mutableListOf(name, descripcion, foto, video, ingrediente)
    var confi = false






        for(i in lista){
            if(i == "") {
                viewmodelA.changeCreateAlerte(true)
                confi = false
                break
            } else confi = true
        }

        if(confi){
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
                        .split(",", " ")
                        .toMutableList(),
                    mutableListOf(),
                    user
                )
                viewmodel.saveNewMeals("CreateMeals", context, {navController.navigate("ok")}) {
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
                        .split(",", " ")
                        .toMutableList(),
                    video,
                    user
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

                }
            }
        }else navController.navigate("createNewMeal")
    }


