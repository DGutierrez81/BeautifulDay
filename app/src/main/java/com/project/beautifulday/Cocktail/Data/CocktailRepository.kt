package com.project.beautifulday.Cocktail.Data

import com.project.beautifulday.Cocktail.CocktailService
import com.project.beautifulday.Cocktail.ui.States.CocktailState
import com.project.beautifulday.Cocktail.ui.States.drinkState
import com.project.beautifulday.Meal.ui.States.ListMealsState
import javax.inject.Inject



class CocktailRepository @Inject constructor(private val api: CocktailService){

    suspend fun vCocktail(name: String): CocktailState {
        //return api.vCocktail()
        val response = api.getCocktail(name)
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else {
            CocktailState()
        }
    }

    suspend fun vCocktailRandom(): CocktailState{
        //return api.vCocktail()
        val response = api.getRandom()
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else {
            CocktailState()
        }
    }

    suspend fun getCategory(category: String): CocktailState{
        val response = api.getCategory(category)
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else{
            CocktailState()
        }
    }

    suspend fun getAlcoholics(election: String): CocktailState{
        val response = api.getAlcoholics(election)
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else{
            CocktailState()
        }
    }

    suspend fun getCocktailById(id: String): CocktailState {
        val response = api.getCocktailById(id)
        return if(response.isSuccessful){
            response.body()?.getDrink() ?: CocktailState()
        }else CocktailState()
    }



    private fun Cocktail.getDrink(): CocktailState{
        return CocktailState(
            drinks = this.drinks?.map { it.getDrinkState() }
        )
    }

    private fun DrinksInfo.getDrinkState(): drinkState {
        return drinkState(
            idDrink = this.idDrink ?: "vacio",
            strDrink = this.strDrink ?: "vacio",
            strInstructions = this.strInstructions ?: "vacio",
            strDrinkThumb = this.strDrinkThumb ?: "vacio",
            strIngredient = mutableListOf(strIngredient1 ?: "", strIngredient2 ?: "", strIngredient3 ?: "", strIngredient4 ?: "", strIngredient5 ?: "", strIngredient6 ?: "", strIngredient7 ?: "",
                strIngredient8 ?: "", strIngredient9 ?: "", strIngredient10 ?: "", strIngredient11 ?: "", strIngredient12 ?: "",
                strIngredient13 ?: "", strIngredient14 ?: "", strIngredient15 ?: "",)
        )
    }
}

