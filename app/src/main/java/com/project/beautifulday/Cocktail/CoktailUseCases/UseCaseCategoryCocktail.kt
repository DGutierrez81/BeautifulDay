package com.project.beautifulday.Cocktail.CoktailUseCases

import com.project.beautifulday.Cocktail.Data.CocktailRepository
import com.project.beautifulday.Cocktail.ui.States.CocktailState
import javax.inject.Inject

class UseCaseCategoryCocktail@Inject constructor(private val repository: CocktailRepository) {
    suspend operator fun invoke(category: String): CocktailState = repository.getCategory(category)
}