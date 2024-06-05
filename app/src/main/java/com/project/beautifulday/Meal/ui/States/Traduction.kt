package com.project.beautifulday.Meal.ui.States

/**
 * Representa el estado de una traducción.
 *
 * @property textToBeTranslate El texto a ser traducido.
 * @property translatedText El texto traducido.
 * @property isButtonEnabled Indica si el botón está habilitado para iniciar la traducción.
 */
data class Traduction(
    val textToBeTranslated: String = "",
    val translatedText: String = "",
    val isButtonEnabled: Boolean = true
)
