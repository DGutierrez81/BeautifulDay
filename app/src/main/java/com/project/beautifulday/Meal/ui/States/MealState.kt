package com.project.beautifulday.Meal.ui.States

/**
 * Representa el estado de una comida.
 *
 * @property idMeal El ID de la comida.
 * @property strMeal El nombre de la comida.
 * @property strDrinkAlternate La alternativa de bebida de la comida.
 * @property strCategory La categoría de la comida.
 * @property strArea El área de origen de la comida.
 * @property strInstructions Las instrucciones para preparar la comida.
 * @property strMealThumb El enlace de la imagen de la comida.
 * @property strTags Las etiquetas asociadas a la comida.
 * @property strYoutube El enlace de YouTube relacionado con la comida.
 * @property strIngredients La lista de ingredientes de la comida.
 * @property strMeasures La lista de medidas de los ingredientes de la comida.
 */
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

