package com.project.beautifulday.Meal.MealUseCases

import com.project.beautifulday.Meal.Data.Response.MealRepository
import com.project.beautifulday.Meal.ui.States.ListMealsState
import com.project.beautifulday.Meal.ui.States.MealState
import javax.inject.Inject

class UseCaseMealId@Inject constructor(private val repository: MealRepository) {
    suspend operator fun invoke(id: String): ListMealsState = repository.getMealById(id)
}