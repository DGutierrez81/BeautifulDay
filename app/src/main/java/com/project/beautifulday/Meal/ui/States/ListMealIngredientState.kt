package com.project.beautifulday.Meal.ui.States

import com.project.beautifulday.Meal.ui.States.IngredientState


data class ListMealIngredientState(
    val meals: List<IngredientState>? = emptyList()
)
