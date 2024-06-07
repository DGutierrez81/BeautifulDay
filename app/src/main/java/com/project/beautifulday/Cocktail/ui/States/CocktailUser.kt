package com.project.beautifulday.Cocktail.ui.States

import java.util.UUID

data class CocktailUser(
    val emailUser: String? = "",
    val idDrink: String? = UUID.randomUUID().toString(),
    val strDrink: String? = "",
    val strInstructions: String? = "",
    val strDrinkThumb: String? = "",
    val strList: MutableList<String>? = mutableListOf(),
    val listVotes: MutableList<String>? = mutableListOf(),
    val strmedia: String? = null,
    val nameUser: String? = "",
    val votes: Int? = 0,
    val puntuacion: Double? = 0.0,
    val idDocument: String? = "",
)
