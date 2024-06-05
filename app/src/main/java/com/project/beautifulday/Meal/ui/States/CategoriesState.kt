package com.project.beautifulday.Meal.ui.States

/**
 * Representa el estado de una categoría.
 *
 * @property idCategory El ID de la categoría.
 * @property strCategory El nombre de la categoría.
 * @property strCategoryThumb La URL de la imagen de la categoría.
 * @property strCategoryDescription La descripción de la categoría.
 */
data class CategoriesState(
    val idCategory: String? = "",
    val strCategory: String? = "",
    val strCategoryThumb: String? = "",
    val strCategoryDescription: String? = ""
)

