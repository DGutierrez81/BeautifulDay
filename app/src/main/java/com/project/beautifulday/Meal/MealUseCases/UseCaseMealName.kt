package com.project.beautifulday.Meal.MealUseCases

import com.project.beautifulday.Meal.Data.Response.MealRepository
import com.project.beautifulday.Meal.ui.States.ListMealsState
import javax.inject.Inject

class UseCaseMealName@Inject constructor(private val repository: MealRepository) {

    suspend operator fun invoke(name: String): ListMealsState = repository.getNameMeal(name)
}