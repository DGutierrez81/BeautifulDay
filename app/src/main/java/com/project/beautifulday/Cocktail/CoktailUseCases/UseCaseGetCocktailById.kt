package com.project.beautifulday.Cocktail.CoktailUseCases

import com.project.beautifulday.Cocktail.Data.CocktailRepository
import com.project.beautifulday.Cocktail.ui.States.CocktailState
import javax.inject.Inject

class UseCaseGetCocktailById@Inject constructor(private val repository: CocktailRepository) {
    suspend operator fun invoke (id: String): CocktailState = repository.getCocktailById(id)
}