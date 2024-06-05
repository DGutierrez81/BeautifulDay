package com.project.beautifulday.Meal.ui.States


/**
 * Representa el estado de una lista de comidas.
 *
 * @property meals La lista de comidas.
 */
data class ListMealsState(
    val meals: List<MealState>? = emptyList()
)

