package com.project.beautifulday.Meal.ui.States

data class MealState(
    val idMeal: String? = "",
    val strMeal: String? = "",
    val strDrinkAlternate: String? = "",
    val strCategory: String? = "",
    val strArea: String? = "",
    val strInstructions: String? = "",
    val strMealThumb: String? = "",
    val strTags: String? = "",
    val strYoutube: String? = "",
    val strIngredients: MutableList<String>? = mutableListOf(),
    val strMeasures: MutableList<String>? = mutableListOf()
)
