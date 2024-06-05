package com.project.beautifulday.Meal.ui.States

/**
 * Representa el estado de una lista de categorías.
 *
 * @property categories La lista de categorías.
 */
data class ListCategoriesState(
    val categories: List<CategoriesState>? = emptyList()
)

