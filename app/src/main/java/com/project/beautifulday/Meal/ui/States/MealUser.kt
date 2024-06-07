package com.project.beautifulday.Meal.ui.States

import java.util.UUID

/**
 * Representa el estado de una comida creada por un usuario.
 *
 * @property idMeal El ID de la comida.
 * @property strMeal El nombre de la comida.
 * @property strCategory La categoría de la comida.
 * @property strArea El área de origen de la comida.
 * @property strInstructions Las instrucciones para preparar la comida.
 * @property strMealThumb El enlace de la imagen de la comida.
 * @property strTags Las etiquetas asociadas a la comida.
 * @property strYoutube El enlace de YouTube relacionado con la comida.
 * @property strIngredients La lista de ingredientes de la comida.
 * @property strMeasures La lista de medidas de los ingredientes de la comida.
 * @property emailUser El correo electrónico del usuario que creó la comida.
 * @property nameUser El nombre del usuario que creó la comida.
 * @property idDocument El ID del documento relacionado con la comida.
 * @property points Los puntos asociados a la comida.
 * @property votes El número de votos recibidos por la comida.
 */
data class MealUser(
    val idMeal: String? = UUID.randomUUID().toString(),
    val strMeal: String? = "",
    val strCategory: String? = "",
    val strArea: String? = "",
    val strInstructions: String? = "",
    val strMealThumb: String? = "",
    val strTags: String? = "",
    val strYoutube: String? = "",
    val strIngredients: MutableList<String>? = mutableListOf(),
    val strMeasures: MutableList<String>? = mutableListOf(),
    val listVotes: MutableList<String>? = mutableListOf(),
    val emailUser: String? = "",
    val nameUser: String? = "",
    val idDocument: String? = "",
    val points: Double? = 0.0,
    val votes: Int? = 0
)

