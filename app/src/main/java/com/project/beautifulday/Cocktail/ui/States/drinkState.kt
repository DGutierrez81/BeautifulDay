package com.project.beautifulday.Cocktail.ui.States

data class drinkState(
    val idDrink: String = "",
    val strDrink: String = "",
    val strInstructions: String? = "",
    val strDrinkThumb: String? = "",
    val strIngredient: MutableList<String> = mutableListOf()
)