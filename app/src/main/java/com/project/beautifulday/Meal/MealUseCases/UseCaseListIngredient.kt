package com.project.beautifulday.Meal.MealUseCases

import com.project.beautifulday.Meal.Data.Response.MealRepository
import com.project.beautifulday.Meal.ui.States.ListMealIngredientState
import com.project.beautifulday.Meal.ui.States.ListMealsState
import javax.inject.Inject

class UseCaseListIngredient@Inject constructor(private val repository: MealRepository) {
    suspend operator fun invoke(): ListMealIngredientState = repository.getListIngredient()
}